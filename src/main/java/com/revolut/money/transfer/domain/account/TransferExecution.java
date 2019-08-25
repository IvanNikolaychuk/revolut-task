package com.revolut.money.transfer.domain.account;

public class TransferExecution {
    private final TransferRequest request;
    private final TransferResult result;

    public TransferExecution(TransferRequest request, TransferResult result) {
        this.request = request;
        this.result = result;
    }

    public TransferRequest getRequest() {
        return request;
    }

    public TransferResult getResult() {
        return result;
    }
}
