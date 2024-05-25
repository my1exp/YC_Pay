package com.yc_pay.Api;

import com.yc_pay.service.DatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static com.yc_pay.Api.CmcApi.makeAPICall;

@Slf4j(topic = "CmcThread")
public class CmcThread extends Thread{
    public void run() {

        while(true){
            String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("start","1"));
            nameValuePairs.add(new BasicNameValuePair("limit","20"));
            nameValuePairs.add(new BasicNameValuePair("convert","USD"));

            try {
                String result = makeAPICall(uri, nameValuePairs);
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("data");
                ArrayList<CryptoCurrency> cryptoCurrencies = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {

                    String name = obj.getJSONArray("data").getJSONObject(i).getString("name");
                    String dateTime = obj.getJSONObject("status").getString("timestamp")
                            .replace("Z", "");
                    LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
                    BigDecimal price = obj.getJSONArray("data").getJSONObject(i).getJSONObject("quote")
                            .getJSONObject("USD").getBigDecimal("price");
                    CryptoCurrency cryptoCurrency = new CryptoCurrency(name, price, localDateTime);
                    cryptoCurrencies.add(cryptoCurrency);
                    log.info(cryptoCurrency.toString());
                }

                DatabaseService.updatePrices(cryptoCurrencies);
                Thread.sleep(1000 * 60 * 5);
                log.info("Prices updated");
            } catch (IOException e) {
                System.out.println("Error: cannot access content - " + e);
            } catch (URISyntaxException e) {
                System.out.println("Error: Invalid URL " + e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
