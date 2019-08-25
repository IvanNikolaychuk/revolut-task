package com.revolut.money.transfer.app.account;

import com.revolut.money.transfer.infrastructure.repository.HibernateAccountRepository;

public class AccountBalanceHandlerFactory {
    private static AccountBalanceHandler handler;

    public synchronized static AccountBalanceHandler create() {
        if (handler == null) {
            handler = new AccountBalanceHandler(new HibernateAccountRepository());
        }
        return handler;
    }
}
