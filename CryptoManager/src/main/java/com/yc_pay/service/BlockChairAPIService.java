package com.yc_pay.service;

import io.micronaut.http.exceptions.UriSyntaxException;
import jakarta.inject.Singleton;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.net.URL;

@Singleton
public class BlockChairAPIService {

    public static String makeAPICall(String walletAddress) throws UriSyntaxException, IOException {

        URIBuilder builder = new URIBuilder();
        URL url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.blockchair.com/ripple/raw/account")
                .path(walletAddress)
                .queryParam("transactions", "true")
                .build()
                .toUri()
                .toURL();

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(String.valueOf(url));

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            String response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return response_content;
        }
    }
}
