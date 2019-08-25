package com.revolut.money.transfer.domain.account;

import com.revolut.money.transfer.domain.account.Money;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MoneyTest {

    @Test(expected = IllegalArgumentException.class)
    public void moneyWithNegativeAmountCanNotBeCreated() {
        new Money(BigDecimal.ONE.negate());
    }

    @Test
    public void moneyCanBeAdded() {
        Money money = new Money(BigDecimal.ONE);
        Money extraMoney = new Money(BigDecimal.TEN);

        Money result = money.add(extraMoney);

        Assert.assertEquals(result, new Money(BigDecimal.ONE.add(BigDecimal.TEN)));
    }

    @Test
    public void moneyCanBeSubtractedWhenBalanceRemainsPositive() {
        Money money = new Money(BigDecimal.TEN);
        Money subtractMoney = new Money(BigDecimal.ONE);

        Money result = money.subtract(subtractMoney);

        Assert.assertEquals(result, new Money(BigDecimal.TEN.subtract(BigDecimal.ONE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void subtractionOfMoneyCanNotEndsWithNegativeBalance() {
        Money money = new Money(BigDecimal.ONE);
        Money subtractMoney = new Money(BigDecimal.TEN);

        money.subtract(subtractMoney);
    }
}
