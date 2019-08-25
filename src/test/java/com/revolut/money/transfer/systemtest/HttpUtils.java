package com.revolut.money.transfer.systemtest;

import com.google.gson.Gson;
import com.revolut.money.transfer.controller.account.TransferResponseDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

class HttpUtils {
    static String contentOf(HttpResponse response) throws IOException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            response.getEntity().writeTo(stream);
            return new String(stream.toByteArray());
        }
    }

    static HttpResponse post(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);

        return client.execute(request);
    }

    static HttpResponse post(String url, String content) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Charset", "utf-8");
        BasicHttpEntity requestEntity = new BasicHttpEntity();
        requestEntity.setContent(new ByteArrayInputStream(content.getBytes()));
        request.setEntity(requestEntity);

        return client.execute(request);
    }

    static HttpResponse get(String url) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        return client.execute(request);
    }
}
