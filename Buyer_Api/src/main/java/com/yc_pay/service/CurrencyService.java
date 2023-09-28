package com.yc_pay.service;

import com.yc_pay.model.CurrencyCrypto;
import jakarta.inject.Singleton;
import java.util.ArrayList;

@Singleton
public class CurrencyService {


    public ArrayList<CurrencyCrypto> getAllCurrencies()
    {
        return DatabaseService.getCurrenciesCrypto();
    }
}
