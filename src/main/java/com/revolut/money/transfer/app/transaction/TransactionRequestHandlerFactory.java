package com.revolut.money.transfer.app.transaction;

import com.revolut.money.transfer.infrastructure.repository.HibernateTransactionRequestRepository;

public class TransactionRequestHandlerFactory {

    public static synchronized TransactionRequestHandler create() {
        return new TransactionRequestHandler(new HibernateTransactionRequestRepository());
    }
}
