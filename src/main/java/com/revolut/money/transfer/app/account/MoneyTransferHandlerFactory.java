package com.revolut.money.transfer.app.account;

import com.revolut.money.transfer.infrastructure.repository.HibernateAccountRepository;
import com.revolut.money.transfer.infrastructure.repository.HibernateTransactionRequestRepository;
import com.revolut.money.transfer.infrastructure.repository.HibernateTransferExecutionRepository;

public class MoneyTransferHandlerFactory {
    private static MoneyTransferHandler handler;

    public static synchronized MoneyTransferHandler create() {
        if (handler == null) {
            handler = new ThreadSafeMoneyTransferHandlerDecorator(
                    new DefaultMoneyTransferHandler(
                            new HibernateAccountRepository(),
                            new HibernateTransactionRequestRepository(),
                            new HibernateTransferExecutionRepository()
                    )
            );
        }

        return handler;
    }

}
