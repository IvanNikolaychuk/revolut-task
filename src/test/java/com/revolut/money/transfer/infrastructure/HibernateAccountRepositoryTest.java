package com.revolut.money.transfer.infrastructure;

import com.revolut.money.transfer.DomainStubs;
import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.Money;
import com.revolut.money.transfer.infrastructure.repository.HibernateAccountRepository;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

public class HibernateAccountRepositoryTest {
    private HibernateAccountRepository repository = new HibernateAccountRepository();

    @Test
    public void shouldObtainAccountWhenItExists() {
        Account account = DomainStubs.accountWith(new Money(new BigDecimal("5.00")));
        DataPreparation.save(account);

        Optional<Account> result = repository.get(account.getId());

        Assert.assertEquals(Optional.of(account), result);
    }

}