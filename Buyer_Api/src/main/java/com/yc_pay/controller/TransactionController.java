package com.yc_pay.controller;

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


    @Get(uri = "/transaction/{hash}&{network}"
            , produces = MediaType.APPLICATION_JSON
    )
    public HttpResponse<Transaction> getTransaction(@PathVariable String hash,
                                                    @PathVariable String network){
        try {
            return HttpResponse.ok(transactionService.getTransaction(hash, network));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }

    @Put(uri = "/transaction/{hash}&{network}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Transaction> createNewTransaction(@PathVariable String hash, @PathVariable String network,
                                                          @Body TransactionRequest transactionRequest)
    {
        try {
            return HttpResponse.ok(transactionService.createTransaction(hash, network, transactionRequest));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }
}