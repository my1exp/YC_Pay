package com.yc_pay.controller;

import com.yc_pay.model.MerchantPayment;
import com.yc_pay.service.DatabaseService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class MerchantPaymentsController {
    private final DatabaseService databaseService;

    public MerchantPaymentsController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Get("/{merchant_Id}/payments")
    public ArrayList<MerchantPayment> getMerchantPayments(@PathVariable String merchant_Id) {
        return DatabaseService.getMerchantPayments(merchant_Id);
    }
}
