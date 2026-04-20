package com.eduardo.transaction_service.infrastructure.repository.jpa;

import com.eduardo.transaction_service.application.filter.TransactionFilter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public final class TransactionSpecification {

    private TransactionSpecification() {}

    public static Specification<TransactionJpaEntity> from(TransactionFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.accountId() != null) {
                predicates.add(cb.equal(root.get("accountId"), filter.accountId()));
            }
            if (filter.type() != null) {
                predicates.add(cb.equal(root.get("type"), filter.type()));
            }
            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }
            if (filter.createdAfter() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.createdAfter()));
            }
            if (filter.createdBefore() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.createdBefore()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
