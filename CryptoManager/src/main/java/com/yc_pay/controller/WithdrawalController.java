package com.yc_pay.controller;


import com.yc_pay.service.WithdrawalService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;

import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;

@Controller()
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @Post("/withdrawal")
    public HttpResponse<String> getWalletToBuyer(@Header String address) {
        if (address.isEmpty()) {
            return HttpResponse.badRequest("Address is empty");
        }else if(withdrawalService.withdrawToAddress(address).equals("No wallets to withdraw from")) {
            return HttpResponse.badRequest("No wallets to withdraw from");
        }else if(withdrawalService.withdrawToAddress(address).equals("Ok")) {
            return HttpResponse.ok("Ok");
        }
        return null;
    }
}
