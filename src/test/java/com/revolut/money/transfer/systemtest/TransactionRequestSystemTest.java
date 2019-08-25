package com.revolut.money.transfer.systemtest;

import com.revolut.money.transfer.Application;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class TransactionRequestSystemTest {
    private static final int PORT = 8090;
    private static final String BASE_URL = "http://localhost:" + PORT + "/";

    private Application application = new Application();

    @Before
    public void setUp() throws Exception {
        application.start(PORT, () -> {});
    }

    @After
    public void shutdown() throws Exception {
        application.stop();
    }

    @Test
    public void uniqueTransactionRequestIdIsGeneratedOnEachRequest() throws IOException {
        HttpResponse firstResponse = HttpUtils.post(BASE_URL + "transactionRequest");
        String firstTransactionId = HttpUtils.contentOf(firstResponse);

        assertEquals(firstResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        assertNotNull(firstTransactionId);

        HttpResponse secondResponse = HttpUtils.post("http://localhost:8090/transactionRequest");
        String secondTransactionId = HttpUtils.contentOf(secondResponse);

        assertEquals(secondResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        assertNotNull(secondTransactionId);
        assertNotEquals(firstTransactionId, secondTransactionId);
    }
}
