package com.yc_pay.controller;

import com.yc_pay.model.CurrencyCrypto;
import com.yc_pay.service.CurrencyService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;

@Controller()
public class CurrenciesController {

    private final CurrencyService currencyService;

    public CurrenciesController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Get(uri = "/Supported_currencies"
            , produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<CurrencyCrypto>> getAllCurrencies (){
        try {
            return HttpResponse.ok(currencyService.getAllCurrencies());
        }catch (Exception e){
            return HttpResponse.notFound();
        }
    }
}
