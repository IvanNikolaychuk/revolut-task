package com.revolut.money.transfer.app.account;

import com.revolut.money.transfer.domain.account.TransferResult;
import de.jkeylockmanager.manager.KeyLockManager;
import de.jkeylockmanager.manager.KeyLockManagers;

import java.math.BigDecimal;

/**
 * Thread-safety is reached by using jkeylock library which provides a lock per key (account id in our domain).
 * Having a lock per account id guarantees us that at one moment of time within 1 account only 1 operation would be performed.
 * Since transfer operation involves 2 accounts, we have to perform lock on both. To avoid deadlock we make a locking processes
 * deterministic - regardless account role in current operation(sender/receiver) first locked account would be the one that has h
 * higher id.
 */
public class ThreadSafeMoneyTransferHandlerDecorator implements MoneyTransferHandler {
    private final MoneyTransferHandler delegate;
    private final KeyLockManager lockManager = KeyLockManagers.newLock();

    ThreadSafeMoneyTransferHandlerDecorator(MoneyTransferHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public TransferResult transfer(String requestId, long senderId, long receiverId, BigDecimal transferAmount) {
        long firstLockKey = senderId > receiverId ? senderId : receiverId;
        long secondLockKey = firstLockKey == senderId ? receiverId : senderId;

        return lockManager.executeLocked(firstLockKey,
                () -> lockManager.executeLocked(secondLockKey,
                        () -> delegate.transfer(requestId, senderId, receiverId, transferAmount)
                )
        );
    }
}