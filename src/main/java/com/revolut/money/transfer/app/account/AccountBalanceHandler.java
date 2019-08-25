package com.revolut.money.transfer.app.account;

import com.revolut.money.transfer.domain.account.AccountRepository;

import java.math.BigDecimal;

public class AccountBalanceHandler {
    private final AccountRepository repository;

    AccountBalanceHandler(AccountRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getBalance(long accountId) {
        return repository.get(accountId).isPresent() ?
                repository.get(accountId).get().getBalance().getAmount() : BigDecimal.ZERO;
    }
}
