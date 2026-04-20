package com.eduardo.transaction_service.domain.model;

import com.eduardo.transaction_service.domain.enums.TransactionStatus;
import com.eduardo.transaction_service.domain.enums.TransactionType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TransactionTest {

    private static final Clock CLOCK = Clock.fixed(Instant.parse("2026-01-01T10:00:00Z"), ZoneOffset.UTC);
    private static final UUID ACCOUNT_ID = UUID.randomUUID();

    private Transaction createValid() {
        return Transaction.create(
                TransactionType.CREDIT,
                new BigDecimal("100.00"),
                ACCOUNT_ID,
                null,
                "Test credit",
                TransactionStatus.COMPLETED,
                CLOCK
        );
    }

    @Nested
    class Create {

        @Test
        void shouldCreateTransactionWithValidData() {
            Transaction t = createValid();

            assertThat(t.getId()).isNotNull();
            assertThat(t.getType()).isEqualTo(TransactionType.CREDIT);
            assertThat(t.getAmount()).isEqualByComparingTo("100.00");
            assertThat(t.getAccountId()).isEqualTo(ACCOUNT_ID);
            assertThat(t.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
            assertThat(t.getCreatedAt()).isNotNull();
            assertThat(t.getVersion()).isNull();
        }

        @Test
        void shouldAllowNullRelatedAccountId() {
            assertThatCode(() -> Transaction.create(TransactionType.DEBIT,
                    new BigDecimal("50.00"), ACCOUNT_ID, null, null,
                    TransactionStatus.COMPLETED, CLOCK))
                    .doesNotThrowAnyException();
        }

        @Test
        void shouldAllowTransferWithRelatedAccount() {
            UUID related = UUID.randomUUID();
            Transaction t = Transaction.create(TransactionType.TRANSFER,
                    new BigDecimal("200.00"), ACCOUNT_ID, related, "Transfer",
                    TransactionStatus.COMPLETED, CLOCK);
            assertThat(t.getRelatedAccountId()).isEqualTo(related);
        }

        @Test
        void shouldRejectNullType() {
            assertThatThrownBy(() -> Transaction.create(null,
                    new BigDecimal("100.00"), ACCOUNT_ID, null, null,
                    TransactionStatus.COMPLETED, CLOCK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("type");
        }

        @Test
        void shouldRejectNullAmount() {
            assertThatThrownBy(() -> Transaction.create(TransactionType.CREDIT,
                    null, ACCOUNT_ID, null, null, TransactionStatus.COMPLETED, CLOCK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Amount");
        }

        @Test
        void shouldRejectZeroAmount() {
            assertThatThrownBy(() -> Transaction.create(TransactionType.CREDIT,
                    BigDecimal.ZERO, ACCOUNT_ID, null, null, TransactionStatus.COMPLETED, CLOCK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Amount");
        }

        @Test
        void shouldRejectNegativeAmount() {
            assertThatThrownBy(() -> Transaction.create(TransactionType.CREDIT,
                    new BigDecimal("-50.00"), ACCOUNT_ID, null, null, TransactionStatus.COMPLETED, CLOCK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Amount");
        }

        @Test
        void shouldRejectNullAccountId() {
            assertThatThrownBy(() -> Transaction.create(TransactionType.CREDIT,
                    new BigDecimal("100.00"), null, null, null, TransactionStatus.COMPLETED, CLOCK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Account ID");
        }

        @Test
        void shouldRejectNullStatus() {
            assertThatThrownBy(() -> Transaction.create(TransactionType.CREDIT,
                    new BigDecimal("100.00"), ACCOUNT_ID, null, null, null, CLOCK))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("status");
        }
    }

    @Nested
    class Immutability {

        @Test
        void eachCreateCallProducesUniqueId() {
            Transaction t1 = createValid();
            Transaction t2 = createValid();
            assertThat(t1.getId()).isNotEqualTo(t2.getId());
        }

        @Test
        void reconstitutedTransactionPreservesAllFields() {
            Transaction original = createValid();
            Transaction reconstituted = Transaction.reconstitute(
                    original.getId(),
                    original.getType(),
                    original.getAmount(),
                    original.getAccountId(),
                    original.getRelatedAccountId(),
                    original.getDescription(),
                    original.getStatus(),
                    original.getCreatedAt(),
                    1L
            );

            assertThat(reconstituted.getId()).isEqualTo(original.getId());
            assertThat(reconstituted.getAmount()).isEqualByComparingTo(original.getAmount());
            assertThat(reconstituted.getVersion()).isEqualTo(1L);
        }
    }

    @Nested
    class Equality {

        @Test
        void transactionsWithSameIdAreEqual() {
            Transaction t = createValid();
            Transaction reconstituted = Transaction.reconstitute(
                    t.getId(), TransactionType.DEBIT, new BigDecimal("999.00"),
                    UUID.randomUUID(), null, null, TransactionStatus.FAILED,
                    t.getCreatedAt(), 0L
            );
            assertThat(t).isEqualTo(reconstituted);
        }

        @Test
        void transactionsWithDifferentIdsAreNotEqual() {
            assertThat(createValid()).isNotEqualTo(createValid());
        }
    }
}
