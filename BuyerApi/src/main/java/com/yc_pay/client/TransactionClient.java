package com.yc_pay.client;

import com.yc_pay.model.TransactionRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8081")
public interface TransactionClient {


    @Put(value = "/transaction/{transactionId}&{merchantId}",
            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    TransactionResponse postBuyerTransaction(@PathVariable String transactionId,
                                             @PathVariable String merchantId,
                                             @Body TransactionRequest transactionRequest);

    @Get(value = "/transaction/{transactionId}&{merchantId}",
            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    TransactionResponse getBuyerTransaction(@PathVariable String transactionId,
                                            @PathVariable String merchantId);


}
