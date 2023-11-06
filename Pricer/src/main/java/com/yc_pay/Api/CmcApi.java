package com.yc_pay.Api;

import jakarta.inject.Singleton;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Singleton
public class CmcApi {

    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        String apiKey = "7e11e445-d09a-474d-86c4-bdfd20f3b094";
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        try (CloseableHttpResponse response = client.execute(request)) {
            HttpEntity entity = response.getEntity();
            String response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return response_content;
        }
    }
}
