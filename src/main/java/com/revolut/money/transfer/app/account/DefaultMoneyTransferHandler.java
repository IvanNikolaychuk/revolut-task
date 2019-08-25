package com.revolut.money.transfer.app.account;

import com.revolut.money.transfer.domain.account.*;
import com.revolut.money.transfer.domain.transaction.TransactionRequest;
import com.revolut.money.transfer.domain.transaction.TransactionRequestRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class DefaultMoneyTransferHandler implements MoneyTransferHandler {
    private final AccountRepository accountRepository;
    private final TransactionRequestRepository transactionRequestRepository;
    private final TransferExecutionRepository transferExecutionRepository;

    public DefaultMoneyTransferHandler(AccountRepository accountRepository,
                                TransactionRequestRepository transactionRequestRepository,
                                TransferExecutionRepository transferExecutionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRequestRepository = transactionRequestRepository;
        this.transferExecutionRepository = transferExecutionRepository;
    }

    @Override
    public TransferResult transfer(String requestId, long senderId, long receiverId, BigDecimal transferAmount) {
        Optional<TransactionRequest> transactionRequest = transactionRequestRepository.get(requestId);
        if (!transactionRequest.isPresent()) {
            return TransferResult.failed(requestId, "Request is not present. Please create it before doing the transfer.");
        } else if (transactionRequest.get().isCompleted()) {
            return TransferResult.failed(requestId, "Request was already completed.");
        }

        Optional<Account> senderAccount = accountRepository.get(senderId);
        Optional<Account> receiverAccount = accountRepository.get(receiverId);
        if (senderAccount.isPresent() && receiverAccount.isPresent()) {
            return transfer(transactionRequest.get(), senderAccount.get(), receiverAccount.get(), new Money(transferAmount));
        } else {
            return TransferResult.failed(requestId, "One of accounts " + senderId + " or " + receiverId + " is not found");
        }
    }

    private TransferResult transfer(TransactionRequest request, Account sender, Account receiver, Money transferAmount) {
        TransferRequest transferRequest = new TransferRequest(request, sender, receiver, transferAmount);
        TransferResult transferResult = sender.transferFromAccount(transferRequest);
        request.complete();

        transferExecutionRepository.save(new TransferExecution(transferRequest, transferResult));

        return transferResult;
    }
}
