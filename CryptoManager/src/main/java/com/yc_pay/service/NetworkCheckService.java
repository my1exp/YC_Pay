package com.yc_pay.service;

import jakarta.inject.Singleton;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

@Singleton
public class NetworkCheckService {

    @SneakyThrows
    public static XrpCurrencyAmount XrpNetworkCheck(String address){

        HttpUrl rippledUrl = HttpUrl.get("https://s1.ripple.com:51234/");
        System.out.println("Constructing an XrplClient connected to " + rippledUrl);
        XrplClient xrplClient = new XrplClient(rippledUrl);

        AccountInfoRequestParams requestParams = AccountInfoRequestParams.of(Address.of(address));
        AccountInfoResult accountInfoResult = xrplClient.accountInfo(requestParams);
        System.out.println(accountInfoResult);
        return accountInfoResult.accountData().balance();
    }
}
