package com.yc_pay.client.pricer;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8083")
public interface PricerClient {
    @Get(value = "/price")
    PricerResponse getPriceToBuyer(@Header String currencyCrypto,
                                   @Header float amountFiat,
                                   @Header String currencyFiat);
}
