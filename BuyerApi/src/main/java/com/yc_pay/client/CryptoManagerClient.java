package com.yc_pay.client;

import com.yc_pay.model.IntentRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8082")
public interface CryptoManagerClient {


//    @Put(value = "/transaction/{transactionId}&{merchantId}",
//            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
//    WalletResponse postBuyerTransaction(@PathVariable String transactionId,
//                                        @PathVariable String merchantId,
//                                        @Body IntentRequest intentRequest);

    @Get(value = "/wallet")
    WalletResponse getWalletToBuyer(@Header String network,
                                    @Header String currency);


}
