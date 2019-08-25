package com.revolut.money.transfer.controller.account;

import com.google.gson.Gson;

import java.math.BigDecimal;

public class TransferRequestDto {
    long senderId;
    long receiverId;
    BigDecimal amount;
    String requestId;

    public TransferRequestDto() {}

    public TransferRequestDto(long senderId, long receiverId, BigDecimal amount, String requestId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.requestId = requestId;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
