package com.revolut.money.transfer;

import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.Money;
import com.revolut.money.transfer.infrastructure.repository.HibernateAccountRepository;

import java.math.BigDecimal;

public class DefaultDataPreparationFunction implements DataPreparationFunction {

    @Override
    public void prepareData() {
        HibernateAccountRepository repository = new HibernateAccountRepository();
        repository.save(new Account(1L, new Money(BigDecimal.TEN)));
        repository.save(new Account(2L, new Money(BigDecimal.ONE)));
    }
}
