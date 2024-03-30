package com.yc_pay.controller;

import com.yc_pay.model.Intent;
import com.yc_pay.model.WalletResponse;
import com.yc_pay.service.WalletService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;

@Controller()
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Get(uri = "/wallet", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public MutableHttpResponse<WalletResponse> getWalletToBuyer(@Header String network,
                                                                @Header String currency,
                                                                @Header float amountCrypto,
                                                                @Header float amountFiat,
                                                                @Header String merchant_id,
                                                                @Header String request_id,
                                                                @Header String session_id) {

        Intent intent = new Intent(network, currency, amountCrypto, amountFiat, merchant_id, request_id, session_id);
        return HttpResponse.ok(walletService.getWalletToBuyer(intent));

    }
}
