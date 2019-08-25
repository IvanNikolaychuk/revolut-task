package com.revolut.money.transfer.app.transaction;

import com.revolut.money.transfer.domain.transaction.TransactionRequest;
import com.revolut.money.transfer.domain.transaction.TransactionRequestRepository;

import java.util.UUID;

public class TransactionRequestHandler {
    private final TransactionRequestRepository repository;

    public TransactionRequestHandler(TransactionRequestRepository repository) {
        this.repository = repository;
    }

    public String createNewTransactionRequest() {
        TransactionRequest request = new TransactionRequest(UUID.randomUUID().toString());
        repository.save(request);
        return request.getId();
    }
}
