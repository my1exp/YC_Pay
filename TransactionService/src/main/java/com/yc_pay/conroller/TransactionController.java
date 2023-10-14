package com.yc_pay.conroller;

import com.yc_pay.model.TransactionRequest;
import com.yc_pay.model.TransactionResponse;
import com.yc_pay.service.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;


@Controller("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Put(uri = "/SendToColdWallet", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<TransactionResponse> postUserTransaction(@PathVariable String transactionId, @PathVariable String merchantId,
                                                                @Body TransactionRequest transactionRequest)
    {
        Object createTransactionFromUser = transactionService.postUserTransaction(transactionId,
                merchantId, transactionRequest);
            return HttpResponse.created((TransactionResponse) createTransactionFromUser);
    }

    @Get(uri = "/{transactionId}&{merchantId}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<TransactionResponse> getUserTransaction(@PathVariable String transactionId, @PathVariable String merchantId)
    {
        try{
            return HttpResponse.ok(transactionService.getUserTransaction(transactionId, merchantId));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }

    }
}
