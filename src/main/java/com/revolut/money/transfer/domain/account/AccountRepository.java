package com.revolut.money.transfer.domain.account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> get(long id);

    void save(Account account);
}
