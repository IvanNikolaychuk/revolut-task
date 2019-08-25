package com.revolut.money.transfer.systemtest;

import com.revolut.money.transfer.Application;
import com.revolut.money.transfer.DomainStubs;
import com.revolut.money.transfer.controller.account.TransferRequestDto;
import com.revolut.money.transfer.controller.account.TransferResponseDto;
import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.Money;
import com.revolut.money.transfer.infrastructure.repository.HibernateAccountRepository;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

import static org.junit.Assert.*;

public class MoneyTransferSystemTest {
    private static final int PORT = 8090;
    private static final String BASE_URL = "http://localhost:" + PORT + "/account/";

    private Application application = new Application();

    private Account accountWithMoney = DomainStubs.accountWith(new Money(new BigDecimal("10.00")));
    private Account accountWithoutMoney = DomainStubs.accountWith(new Money(new BigDecimal("0.00")));

    @Before
    public void setUp() throws Exception {
        application.start(PORT, () -> {
            new HibernateAccountRepository().save(accountWithMoney);
            new HibernateAccountRepository().save(accountWithoutMoney);
        });
    }

    @After
    public void shutdown() throws Exception {
        application.stop();
    }

    @Test
    public void accountBalanceCanBeQueried() throws IOException {
        HttpResponse response = HttpUtils.get(BASE_URL + accountWithMoney.getId() + "/balance");

        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        assertEquals(new BigDecimal(HttpUtils.contentOf(response)), accountWithMoney.getBalance().getAmount());
    }

    @Test
    public void moneyCanBeTransferredWhenThereAreEnoughFounds() throws Exception {
        String transactionId = HttpUtils.contentOf(HttpUtils.post("http://localhost:8090/transactionRequest"));

        BigDecimal transferAmount = BigDecimal.TEN;
        String request = new TransferRequestDto(
                accountWithMoney.getId(), accountWithoutMoney.getId(), transferAmount, transactionId)
                .toJson();

        System.out.println(request);

        HttpResponse response = HttpUtils.post(BASE_URL + "transfer", request);

        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        TransferResponseDto transferResponseDto = TransferResponseDto.fromJson(HttpUtils.contentOf(response));
        assertTrue(transferResponseDto.isSuccessful());

        String balanceOfSender = HttpUtils.contentOf(HttpUtils.get(BASE_URL + accountWithMoney.getId() + "/balance"));
        assertEquals(new BigDecimal(balanceOfSender), accountWithMoney.getBalance().getAmount().subtract(transferAmount));

        String balanceOfReceiver = HttpUtils.contentOf(HttpUtils.get(BASE_URL + accountWithoutMoney.getId() + "/balance"));
        assertEquals(new BigDecimal(balanceOfReceiver), accountWithoutMoney.getBalance().getAmount().add(transferAmount));
    }

    @Test
    public void moneyCanNotBeTransferredWhenThereAreNotEnoughFounds() throws IOException {
        String transactionId = HttpUtils.contentOf(HttpUtils.post("http://localhost:8090/transactionRequest"));

        String request = new TransferRequestDto(
                accountWithoutMoney.getId(), accountWithMoney.getId(), BigDecimal.TEN, transactionId)
                .toJson();

        HttpResponse response = HttpUtils.post(BASE_URL + "transfer", request);

        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        TransferResponseDto transferResponseDto = TransferResponseDto.fromJson(HttpUtils.contentOf(response));
        assertFalse(transferResponseDto.isSuccessful());
        assertTrue(transferResponseDto.getDetails().contains("Insufficient funds"));
    }

    @Test
    public void moneyCanNotBeTransferredWhenTheIsNoTransactionRequest() throws IOException {
        String request = new TransferRequestDto(
                accountWithMoney.getId(), accountWithoutMoney.getId(), BigDecimal.TEN, UUID.randomUUID().toString()
        ).toJson();

        HttpResponse response = HttpUtils.post(BASE_URL + "transfer", request);

        assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        TransferResponseDto transferResponseDto = TransferResponseDto.fromJson(HttpUtils.contentOf(response));
        assertFalse(transferResponseDto.isSuccessful());
        assertTrue(transferResponseDto.getDetails().contains("Request is not present"));
    }
}
