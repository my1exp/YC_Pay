package com.yc_pay.controller;

import com.yc_pay.model.ExchangeResponse;
import com.yc_pay.service.ExchangeRequestService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;


@Controller()
public class ExchangeRequestController {

    private final ExchangeRequestService exchangeRequestService;

    public ExchangeRequestController(ExchangeRequestService exchangeRequestService) {
        this.exchangeRequestService = exchangeRequestService;
    }


    @Get(uri = "/amountToPay/"
            , produces = MediaType.APPLICATION_JSON
    )
    public HttpResponse<ExchangeResponse> getExchangeRequest(@Header String currency_crypto,
                                                             @Header float amount_crypto,
                                                             @Header String currency_fiat){
        try {
            return HttpResponse.ok(exchangeRequestService.returnAmountFiat(currency_crypto, amount_crypto, currency_fiat));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }
}