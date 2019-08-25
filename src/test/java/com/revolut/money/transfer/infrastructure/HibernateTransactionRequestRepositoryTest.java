package com.revolut.money.transfer.infrastructure;

import com.revolut.money.transfer.domain.transaction.TransactionRequest;
import com.revolut.money.transfer.infrastructure.repository.HibernateTransactionRequestRepository;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

public class HibernateTransactionRequestRepositoryTest {

    @Test
    public void requestNotPresentWhenItDoesNotExist() {
        HibernateTransactionRequestRepository repository = new HibernateTransactionRequestRepository();

        Assert.assertEquals(repository.get(UUID.randomUUID().toString()), Optional.empty());
    }

    @Test
    public void requestIsPresentWhenItExists() {
        HibernateTransactionRequestRepository repository = new HibernateTransactionRequestRepository();
        TransactionRequest request = new TransactionRequest(UUID.randomUUID().toString());

        repository.save(request);

        Assert.assertEquals(repository.get(request.getId()), Optional.of(request));
    }
}
