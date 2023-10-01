package com.yc_pay.client;

import com.yc_pay.model.TransactionRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;

@Client("http://localhost:8081")
public interface TransactionClient {


    @Put(value = "/transaction/{transactionId}&{merchantId}",
            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    TransactionResponse postBuyerTransactions(@PathVariable String transactionId,
                                     @PathVariable String merchantId,
                                     @Body TransactionRequest transactionRequest);

}
