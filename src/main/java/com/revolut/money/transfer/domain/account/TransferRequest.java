package com.revolut.money.transfer.domain.account;

import com.revolut.money.transfer.domain.transaction.TransactionRequest;

public class TransferRequest {
    private TransactionRequest request;
    Account sender;
    Account receiver;
    Money transferAmount;

    public TransferRequest(TransactionRequest request, Account sender, Account receiver, Money transferAmount) {
        this.request = request;
        this.sender = sender;
        this.receiver = receiver;
        this.transferAmount = transferAmount;
    }

    public TransactionRequest getTransactionRequest() {
        return request;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    String getId() {
        return request.getId();
    }
}
