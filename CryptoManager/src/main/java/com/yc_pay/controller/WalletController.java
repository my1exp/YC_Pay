package com.yc_pay.controller;

import com.yc_pay.model.StatusResponse;
import com.yc_pay.model.WalletResponse;
import com.yc_pay.service.WalletService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;

@Controller()
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Get("/wallet")
    public HttpResponse<WalletResponse> getWalletToBuyer(@Header String network,
                                                         @Header String currency,
                                                         @Header float amountCrypto)
    {
        return HttpResponse.ok(walletService.getWalletToBuyer(network, currency, amountCrypto));
    }

    @Get("/status")
    public HttpResponse<StatusResponse> getStatusToBuyer(@Header int walletId)
    {
        return HttpResponse.ok(walletService.getStatusToBuyer(walletId));
    }
}
