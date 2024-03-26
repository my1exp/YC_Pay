package com.yc_pay.conroller;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import com.yc_pay.service.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import java.util.ArrayList;


@Controller()
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Get(uri = "/transactions", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ArrayList<TransactionResponse>> getUserTransaction(@Header String merchantId) {
        try {
            return HttpResponse.ok(transactionService.getUserTransaction(merchantId));
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }

    @Post(uri = "/transactions", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<String> createTransaction(@Body Transaction transaction) {
        try {
            return HttpResponse.ok(transactionService.createTransaction(transaction));
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }
}
