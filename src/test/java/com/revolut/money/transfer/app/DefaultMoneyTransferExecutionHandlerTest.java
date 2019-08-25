package com.revolut.money.transfer.app;

import com.revolut.money.transfer.DomainStubs;
import com.revolut.money.transfer.app.account.DefaultMoneyTransferHandler;
import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.Money;
import com.revolut.money.transfer.domain.account.TransferResult;
import com.revolut.money.transfer.domain.transaction.TransactionRequest;
import com.revolut.money.transfer.infrastructure.DataPreparation;
import com.revolut.money.transfer.infrastructure.repository.HibernateAccountRepository;
import com.revolut.money.transfer.infrastructure.repository.HibernateTransactionRequestRepository;
import com.revolut.money.transfer.infrastructure.repository.HibernateTransferExecutionRepository;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class DefaultMoneyTransferExecutionHandlerTest {

    @Test
    public void shouldTransferMoneyWhenBothAccountsExist() {
        BigDecimal transferAmount = BigDecimal.ONE;
        Account sender = DomainStubs.accountWith(new Money(BigDecimal.TEN));
        Account receiver = DomainStubs.accountWithZeroBalance();
        DataPreparation.save(sender, receiver);

        HibernateTransactionRequestRepository transactionRequestRepository = new HibernateTransactionRequestRepository();
        TransactionRequest transactionRequest = new TransactionRequest(UUID.randomUUID().toString());
        transactionRequestRepository.save(transactionRequest);

        HibernateAccountRepository accountRepository = new HibernateAccountRepository();
        DefaultMoneyTransferHandler handler = new DefaultMoneyTransferHandler(
                accountRepository,
                transactionRequestRepository,
                new HibernateTransferExecutionRepository()
        );

        TransferResult result = handler.transfer(transactionRequest.getId(), sender.getId(), receiver.getId(), transferAmount);

        Assert.assertTrue(result.isSuccessful());
    }
}