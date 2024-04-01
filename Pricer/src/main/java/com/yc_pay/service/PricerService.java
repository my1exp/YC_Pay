package com.yc_pay.service;

import com.yc_pay.model.PricerResponse;
import jakarta.inject.Singleton;

import java.util.Objects;

@Singleton
public class PricerService {

    public PricerResponse getAmountCrypto (String currencyCrypto, float amountFiat, String currencyFiat){

        float cryptoPrice = Objects.requireNonNull(DatabaseService.getPrice(currencyCrypto)).floatValue();
        //ToDo сделать таблицу для пересчета USD в другую валюту

        return new PricerResponse(amountFiat/cryptoPrice);
    }

    public double getMarketPriceCrypto(String currencyCrypto){
        return Objects.requireNonNull(DatabaseService.getPrice(currencyCrypto)).doubleValue();
    }
}
