package com.revolut.money.transfer.domain.account;

import com.revolut.money.transfer.domain.account.TransferResult;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TransferExecutionResultTest {

    @Test
    public void successfulTransferResultHasNoDescription() {
        String requestId = UUID.randomUUID().toString();
        TransferResult transferResult = TransferResult.success(requestId);

        Assert.assertEquals(transferResult.getRequestId(), requestId);
        Assert.assertTrue(transferResult.isSuccessful());
        Assert.assertEquals(transferResult.getDetails(), "");
    }

    @Test
    public void failedTransferResultHasDescription() {
        String requestId = UUID.randomUUID().toString();
        String details = "Not enough founds";
        TransferResult transferResult = TransferResult.failed(requestId, details);

        Assert.assertEquals(transferResult.getRequestId(), requestId);
        Assert.assertTrue(!transferResult.isSuccessful());
        Assert.assertEquals(transferResult.getDetails(), details);
    }

}
