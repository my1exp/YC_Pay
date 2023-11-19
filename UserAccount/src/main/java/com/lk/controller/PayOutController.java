package com.lk.controller;

import com.lk.model.Payout;
import com.lk.model.Payouts;
import com.lk.service.PayoutService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller("/Payout")
public class PayOutController {

    @Inject
    PayoutService payoutsService;

    @Get(produces = MediaType.APPLICATION_JSON)
    public Payouts getPayouts() {
        return payoutsService.getPayuots();
    }

    @Post
    public void createPayout(@Body Payout payout) {
        payoutsService.createPayout(payout.getPayoutID(), payout.getAmountFiat(), payout.getAddressTo());
    }
}
