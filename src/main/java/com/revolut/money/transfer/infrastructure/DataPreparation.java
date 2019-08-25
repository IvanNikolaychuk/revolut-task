package com.revolut.money.transfer.infrastructure;

import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.infrastructure.repository.SessionProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;

public class DataPreparation {
    public static void save(Account... accounts) {
        try (Session session = SessionProvider.get().getSession()) {
            Transaction transaction = session.beginTransaction();
            Arrays.asList(accounts).forEach(session::save);
            transaction.commit();
        }
    }
}
