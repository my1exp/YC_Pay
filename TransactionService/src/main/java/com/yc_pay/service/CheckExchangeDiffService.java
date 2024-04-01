package com.yc_pay.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CheckExchangeDiffService {

    public static String getMarketPriceCrypto(String currency_crypto)
            throws IOException, InterruptedException {

        URI uri = URI.create("http://localhost:8083/getMarketPriceCrypto");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .header("currency_crypto", currency_crypto)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        }else{
            return null;
        }
    }
}
