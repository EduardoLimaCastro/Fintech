package com.eduardo.transfer_service.infrastructure.web.controller;

import com.eduardo.transfer_service.application.dto.request.CreateTransferRequest;
import com.eduardo.transfer_service.application.dto.response.TransferResponse;
import com.eduardo.transfer_service.application.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponse> initiate(@Valid @RequestBody CreateTransferRequest request) {
        TransferResponse response = transferService.initiate(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(transferService.findById(id));
    }
}
