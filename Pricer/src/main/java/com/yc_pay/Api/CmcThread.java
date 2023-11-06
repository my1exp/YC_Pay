package com.yc_pay.Api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.yc_pay.Api.CmcApi.makeAPICall;

public class CmcThread extends Thread{
    public void run() {

        while(true){
            String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("start","1"));
            nameValuePairs.add(new BasicNameValuePair("limit","20"));
            nameValuePairs.add(new BasicNameValuePair("convert","USD"));

            try {
                String result = makeAPICall(uri, nameValuePairs);

                String jsonString = result ; //assign your JSON String here
                JSONObject obj = new JSONObject(jsonString);
                JSONArray arr = obj.getJSONArray("data");
                // ходить в базу и забирать названия крипты
                //класть их в array

                for (int i = 0; i < arr.length(); i++) {
                    if()
                }


                String arr = obj.getJSONArray("data").getJSONObject(0).getString("name");



                System.out.println(result);
                System.out.println(arr);
//                for (int i = 0; i < arr.length(); i++) {
//                    arr[i].
//                }
//                System.out.println(arr.length());


                Thread.sleep(1000 * 60 * 2);
            } catch (IOException e) {
                System.out.println("Error: cannot access content - " + e.toString());
            } catch (URISyntaxException e) {
                System.out.println("Error: Invalid URL " + e.toString());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
