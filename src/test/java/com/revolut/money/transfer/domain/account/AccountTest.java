package com.revolut.money.transfer.domain.account;

import com.revolut.money.transfer.DomainStubs;
import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.Money;
import com.revolut.money.transfer.domain.account.TransferRequest;
import com.revolut.money.transfer.domain.account.TransferResult;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.revolut.money.transfer.DomainStubs.*;
import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void accountHasBalance() {
        Money money = new Money(BigDecimal.TEN);
        long accountId = 1L;
        Account account = new Account(accountId, money);

        assertEquals(account.getBalance(), money);
        assertEquals(account.getId(), accountId);
    }

    @Test
    public void moneyCanBeTransferredWhenAccountHasEnoughMoney() {
        Money senderAmountBeforeTransfer = new Money(BigDecimal.TEN);
        Account sender = accountWith(senderAmountBeforeTransfer);

        Money receiverAmountBeforeTransfer = new Money(BigDecimal.ONE);
        Account receiver = accountWith(receiverAmountBeforeTransfer);

        Money amountOfTransfer = new Money(BigDecimal.ONE);
        TransferRequest request = new TransferRequest(aTransactionRequest(), sender, receiver, amountOfTransfer);

        TransferResult result = sender.transferFromAccount(request);

        Assert.assertTrue(result.isSuccessful());
        Assert.assertEquals(sender.getBalance(), senderAmountBeforeTransfer.subtract(amountOfTransfer));
        Assert.assertEquals(receiver.getBalance(), receiverAmountBeforeTransfer.add(amountOfTransfer));
    }

    @Test
    public void moneyCanNotBeTransferredWhenAccountHasNotEnoughMoney() {
        Money senderAmountBeforeTransfer = new Money(BigDecimal.ONE);
        Account sender = accountWith(senderAmountBeforeTransfer);

        Money receiverAmountBeforeTransfer = new Money(BigDecimal.TEN);
        Account receiver = accountWith(receiverAmountBeforeTransfer);

        Money amountOfTransfer = new Money(BigDecimal.TEN);

        TransferRequest request = new TransferRequest(aTransactionRequest(), sender, receiver, amountOfTransfer);
        TransferResult result = sender.transferFromAccount(request);

        Assert.assertTrue(!result.isSuccessful());
        Assert.assertEquals(sender.getBalance(), senderAmountBeforeTransfer);
        Assert.assertEquals(receiver.getBalance(), receiverAmountBeforeTransfer);
    }

}
