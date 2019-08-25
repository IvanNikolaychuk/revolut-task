package com.revolut.money.transfer.domain.account;

import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Money {
    @Column
    private BigDecimal amount;

    // needed for hibernate
    Money() {}

    public Money(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can not create funds with negative transferAmount " + amount.doubleValue());
        }
        this.amount = amount;
    }

    Money add(Money otherMoney) {
        return new Money(amount.add(otherMoney.amount));
    }

    Money subtract(Money otherMoney) {
        if (isLessThan(otherMoney)) {
            throw new IllegalArgumentException("Can not subtract " + otherMoney.amount.doubleValue()
                    + " from " + amount.doubleValue() + " as it would lead to negative balance");
        }

        return new Money(amount.subtract(otherMoney.amount));
    }

    boolean isLessThan(Money otherMoney) {
        return otherMoney.amount.compareTo(amount) > 0;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Money{" +
                "transferAmount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
