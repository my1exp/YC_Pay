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


    @Get(uri = "/amountToPay/currency_crypto={currencyCrypto}&amount_fiat={amountCrypto}&currency_fiat={currencyFiat}"
            , produces = MediaType.APPLICATION_JSON
    )
    public HttpResponse<ExchangeResponse> getExchangeRequest(@PathVariable String currencyCrypto,
                                                             float amountCrypto,
                                                             String currencyFiat){
        //ToDo try
        return HttpResponse.ok(exchangeRequestService.returnAmountFiat(currencyCrypto, amountCrypto, currencyFiat));
        //Todo catch
        //return HttpResponse.badRequest()
    }
}