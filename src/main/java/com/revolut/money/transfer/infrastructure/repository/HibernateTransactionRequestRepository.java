package com.revolut.money.transfer.infrastructure.repository;

import com.revolut.money.transfer.domain.transaction.TransactionRequest;
import com.revolut.money.transfer.domain.transaction.TransactionRequestRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class HibernateTransactionRequestRepository implements TransactionRequestRepository {

    @Override
    public Optional<TransactionRequest> get(String requestId) {
        try (Session session = SessionProvider.get().getSession()) {
            TransactionRequest request = session.find(TransactionRequest.class, requestId);
            return request == null ? Optional.empty() : Optional.of(request);
        }
    }

    @Override
    public void save(TransactionRequest request) {
        try (Session session = SessionProvider.get().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(request);
            transaction.commit();
        }
    }
}
