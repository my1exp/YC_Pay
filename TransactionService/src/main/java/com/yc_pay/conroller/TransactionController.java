package com.yc_pay.conroller;

import com.yc_pay.model.TransactionRequest;
import com.yc_pay.model.TransactionResponse;
import com.yc_pay.service.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;


@Controller("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Put(uri = "/{transactionId}&{merchantId}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<TransactionResponse> createNewTransaction(@PathVariable String transactionId, @PathVariable String merchantId,
                                                                  @Body TransactionRequest transactionRequest)
    {
        Object createTransactionFromUser = transactionService.createTransactionFromUser(transactionId,
                merchantId, transactionRequest);
            return HttpResponse.created((TransactionResponse) createTransactionFromUser);
    }
}
