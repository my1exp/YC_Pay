package com.yc_pay.controller;

import com.yc_pay.model.PricerResponse;
import com.yc_pay.service.PricerService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;

@Controller
public class PricerController {

    private final PricerService pricerService;

    public PricerController(PricerService pricerService) {
        this.pricerService = pricerService;
    }

    @Get(uri = "/price", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<PricerResponse> getAllCurrencies(@Header String currencyCrypto,
                                                         @Header float amountFiat,
                                                         @Header String currencyFiat) {
        try {
            return HttpResponse.ok(pricerService.getAmountCrypto(currencyCrypto, amountFiat, currencyFiat));
        } catch (Exception e) {
            return HttpResponse.notFound();
        }
    }
}
