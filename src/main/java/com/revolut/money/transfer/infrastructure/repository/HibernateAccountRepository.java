package com.revolut.money.transfer.infrastructure.repository;

import com.revolut.money.transfer.domain.account.Account;
import com.revolut.money.transfer.domain.account.AccountRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class HibernateAccountRepository implements AccountRepository {

    @Override
    public Optional<Account> get(long id) {
        try (Session session = SessionProvider.get().getSession()) {
            Account account = session.find(Account.class, id);
            return account == null ? Optional.empty() : Optional.of(account);
        }
    }

    @Override
    public void save(Account account) {
        try (Session session = SessionProvider.get().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
        }
    }
}
