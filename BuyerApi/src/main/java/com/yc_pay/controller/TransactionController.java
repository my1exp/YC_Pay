package com.yc_pay.controller;

import com.yc_pay.client.TransactionResponse;
import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionRequest;
import com.yc_pay.service.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller()
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService ) {
        this.transactionService = transactionService;
    }


    @Get(uri = "/transaction/{transactionId}&{merchantId}"
            , produces = MediaType.APPLICATION_JSON
    )
    public HttpResponse<Transaction> getTransaction(@PathVariable String transactionId,
                                                    @PathVariable String merchantId){
        try {
            return HttpResponse.ok(transactionService.getTransaction(transactionId, merchantId));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }

    @Put(uri = "/transaction/{transactionId}&{merchantId}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<TransactionResponse> createNewTransaction(@PathVariable String transactionId, @PathVariable String merchantId,
                                                                  @Body TransactionRequest transactionRequest)
    {
        try {
            return HttpResponse.created(transactionService.postBuyerTransaction(transactionId, merchantId, transactionRequest));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }
}