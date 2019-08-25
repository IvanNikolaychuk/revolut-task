package com.revolut.money.transfer.app.account;

import com.revolut.money.transfer.domain.account.TransferResult;

import java.math.BigDecimal;

public interface MoneyTransferHandler {
    TransferResult transfer(String requestId, long senderId, long receiverId, BigDecimal transferAmount);
}
