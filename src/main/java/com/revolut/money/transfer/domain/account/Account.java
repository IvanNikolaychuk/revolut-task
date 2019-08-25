package com.revolut.money.transfer.domain.account;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Account {
    @Id
    private long id;
    @Embedded
    private Money balance;

    // need for hibernate
    Account() {}

    public Account(long id, Money balance) {
        this.balance = balance;
        this.id = id;
    }

    public TransferResult transferFromAccount(TransferRequest request) {
        if (request.sender.id != id) {
            return TransferResult.failed(request.getId(), "Sender in request is: " + request.getId()
                    + ". There was an attempt to transfer it from different account id: " + id);
        }
        if (balance.isLessThan(request.transferAmount)) {
            return TransferResult.failed(
                    request.getId(),
                    "Insufficient funds. Can not transfer " + request.transferAmount + ". Current balance is " + balance
            );
        }

        balance = balance.subtract(request.transferAmount);
        request.receiver.balance = request.receiver.balance.add(request.transferAmount);

        return TransferResult.success(request.getId());
    }

    public Money getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id &&
                Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, balance);
    }
}
