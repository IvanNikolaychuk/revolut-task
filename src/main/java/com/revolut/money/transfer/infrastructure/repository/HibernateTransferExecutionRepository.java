package com.revolut.money.transfer.infrastructure.repository;

import com.revolut.money.transfer.domain.account.TransferExecution;
import com.revolut.money.transfer.domain.account.TransferExecutionRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTransferExecutionRepository implements TransferExecutionRepository {

    @Override
    public void save(TransferExecution transferExecution) {
        try (Session session = SessionProvider.get().getSession()) {
            Transaction transaction = session.beginTransaction();

            session.update(transferExecution.getRequest().getTransactionRequest());

            if (transferExecution.getResult().isSuccessful()) {
                session.update(transferExecution.getRequest().getSender());
                session.update(transferExecution.getRequest().getReceiver());
            }

            transaction.commit();
        }
    }
}
