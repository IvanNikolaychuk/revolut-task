package com.revolut.money.transfer.domain.transaction;

import java.util.Optional;

public interface TransactionRequestRepository {
    Optional<TransactionRequest> get(String requestId);

    void save(TransactionRequest request);
}
