package com.eduardo.transfer_service.application.service;

import com.eduardo.transfer_service.application.dto.event.AccountCreditedEvent;
import com.eduardo.transfer_service.application.dto.event.AccountDebitedEvent;
import com.eduardo.transfer_service.application.dto.event.AccountRefundedEvent;
import com.eduardo.transfer_service.application.dto.event.CreditAccountCommand;
import com.eduardo.transfer_service.application.dto.event.CreditFailedEvent;
import com.eduardo.transfer_service.application.dto.event.DebitFailedEvent;
import com.eduardo.transfer_service.application.dto.event.RefundAccountCommand;
import com.eduardo.transfer_service.application.dto.event.TransferRequestedEvent;
import com.eduardo.transfer_service.application.dto.request.CreateTransferRequest;
import com.eduardo.transfer_service.application.dto.response.TransferResponse;
import com.eduardo.transfer_service.application.mapper.TransferMapper;
import com.eduardo.transfer_service.application.port.out.TransferEventPublisherPort;
import com.eduardo.transfer_service.application.port.out.TransferRepositoryPort;
import com.eduardo.transfer_service.domain.exceptions.TransferNotFoundException;
import com.eduardo.transfer_service.domain.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService {

    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    private final TransferRepositoryPort repository;
    private final TransferEventPublisherPort eventPublisher;
    private final Clock clock;

    @Transactional
    public TransferResponse initiate(CreateTransferRequest request) {
        Transfer transfer = TransferMapper.toDomain(request, clock);
        Transfer saved = repository.save(transfer);

        log.info("Transfer initiated: id={}, source={}, target={}, amount={}",
                saved.getId(), saved.getSourceAccountId(), saved.getTargetAccountId(), saved.getAmount());

        eventPublisher.publishTransferRequested(new TransferRequestedEvent(
                saved.getId(), saved.getSourceAccountId(),
                saved.getTargetAccountId(), saved.getAmount()
        ));

        return TransferMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TransferResponse findById(UUID id) {
        return repository.findById(id)
                .map(TransferMapper::toResponse)
                .orElseThrow(() -> new TransferNotFoundException(id));
    }

    // ── Saga Steps ──────────────────────────────────────────────────────────

    @Transactional
    public void onAccountDebited(AccountDebitedEvent event) {
        Transfer transfer = loadOrWarn(event.transferId());
        if (transfer == null) return;

        transfer.markDebited(clock);
        repository.save(transfer);
        log.info("Transfer {} debited — publishing credit.account", event.transferId());

        eventPublisher.publishCreditAccount(new CreditAccountCommand(
                transfer.getId(), transfer.getTargetAccountId(), transfer.getAmount()
        ));
    }

    @Transactional
    public void onDebitFailed(DebitFailedEvent event) {
        Transfer transfer = loadOrWarn(event.transferId());
        if (transfer == null) return;

        transfer.markFailed(event.reason(), clock);
        repository.save(transfer);
        log.warn("Transfer {} failed at debit step: {}", event.transferId(), event.reason());
    }

    @Transactional
    public void onAccountCredited(AccountCreditedEvent event) {
        Transfer transfer = loadOrWarn(event.transferId());
        if (transfer == null) return;

        transfer.markCompleted(clock);
        repository.save(transfer);
        log.info("Transfer {} completed successfully", event.transferId());
    }

    @Transactional
    public void onCreditFailed(CreditFailedEvent event) {
        Transfer transfer = loadOrWarn(event.transferId());
        if (transfer == null) return;

        transfer.markCompensating(event.reason(), clock);
        repository.save(transfer);
        log.warn("Transfer {} credit failed — initiating compensation: {}", event.transferId(), event.reason());

        eventPublisher.publishRefundAccount(new RefundAccountCommand(
                transfer.getId(), transfer.getSourceAccountId(), transfer.getAmount()
        ));
    }

    @Transactional
    public void onAccountRefunded(AccountRefundedEvent event) {
        Transfer transfer = loadOrWarn(event.transferId());
        if (transfer == null) return;

        transfer.markFailed("Compensation completed after credit failure", clock);
        repository.save(transfer);
        log.info("Transfer {} marked FAILED after successful compensation", event.transferId());
    }

    private Transfer loadOrWarn(UUID transferId) {
        return repository.findById(transferId).orElseGet(() -> {
            log.error("Transfer not found during saga step: {}", transferId);
            return null;
        });
    }
}
