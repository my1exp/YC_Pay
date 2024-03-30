package com.yc_pay.service.delivery;

import jakarta.inject.Singleton;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Singleton
public class StatusDeliveryService {

    public static String statusDelivery(String requestId, String sessionId) throws IOException, InterruptedException {

        URI uri = URI.create("http://localhost:8080/update_intent");
        String requestBody = "{ \"key\": \"value\" }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("requestId", requestId)
                .header("sessionId", sessionId)
                .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }else{
                return "error";
            }
    }
}
