package com.yc_pay.service.delivery;

import jakarta.inject.Singleton;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public class TransactionDeliveryService {
    public static void deliveryUnidentifiedTransaction(double paid_amount_crypto, String currency_crypto)
            throws IOException, InterruptedException {

        URI uri = URI.create("http://tx_service:9090/transaction/unidentified");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{")
                .append("\"paid_amount_crypto\":")
                .append(paid_amount_crypto)
                .append(",")
                .append("\"currency_crypto\":\"")
                .append(currency_crypto)
                .append("}");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(stringBuilder)))
                .header("paid_amount_crypto", String.valueOf(paid_amount_crypto))
                .header("currency_crypto", currency_crypto)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            response.body();
        }
    }

    public static void deliveryIdentifiedTransaction(String merchant_id, double paid_amount_crypto, String currency_crypto,
                                                     double required_amount_crypto, double amount_fiat)
            throws IOException, InterruptedException {

        URI uri = URI.create("http://tx_service:9090/transaction/identified");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{").append("\"merchant_id\":").append(merchant_id).append(",")
                .append("{").append("\"currency_crypto\":").append(currency_crypto).append(",")
                .append("{").append("\"paid_amount_crypto\":").append(paid_amount_crypto).append(",")
                .append("{").append("\"required_amount_crypto\":").append(required_amount_crypto).append(",")
                .append("{").append("\"currency_fiat\":").append("USD").append(",")
                .append("{").append("\"amount_fiat\":").append(amount_fiat)
                .append("}");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(stringBuilder)))
                .header("paid_amount_crypto", String.valueOf(paid_amount_crypto))
                .header("currency_crypto", currency_crypto)
                .header("required_amount_crypto", String.valueOf(required_amount_crypto))
                .header("currency_fiat", "USD")
                .header("amount_fiat", String.valueOf(amount_fiat))
                .header("merchant_id", merchant_id)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            response.body();
        }
    }


}
