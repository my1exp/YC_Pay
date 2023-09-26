package com.yc_pay.service;

import com.yc_pay.model.CurrencyCrypto;
import com.yc_pay.model.Network;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CurrencyService {

    public List<CurrencyCrypto> getAllCurrencies()
    {
        //ToDo по запросу должен ходить в базу и просматривать названия и сети криптовалют есть в ней
        //ToDo на выход должен отдавать верную структуру

        CurrencyCrypto currencyCrypto = new CurrencyCrypto();
        currencyCrypto.setName("BTC");
        List<Network> networkList = new ArrayList<>();
        Network network = new Network("native");
        networkList.add(network);
        currencyCrypto.setNetworkList(networkList);
        List<CurrencyCrypto> currenciesList = new ArrayList<>();
        currenciesList.add(currencyCrypto);

        return currenciesList;
    }
}
