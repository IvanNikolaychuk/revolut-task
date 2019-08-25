package com.revolut.money.transfer;

import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.Money;
import com.revolut.money.transfer.domain.transaction.TransactionRequest;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class DomainStubs {
    public static Account accountWithZeroBalance() {
        return accountWith(new Money(BigDecimal.ZERO));
    }

    public static Account accountWith(Money money) {
        Random random = new Random();
        return new Account(random.nextLong(), money);
    }

    public static TransactionRequest aTransactionRequest() {
        return new TransactionRequest(UUID.randomUUID().toString());
    }

}
