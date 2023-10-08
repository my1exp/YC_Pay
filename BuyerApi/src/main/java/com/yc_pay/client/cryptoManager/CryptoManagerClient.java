package com.yc_pay.client.cryptoManager;

import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8082")
public interface CryptoManagerClient {


//    @Put(value = "/transaction/{transactionId}&{merchantId}"
//    WalletResponse postBuyerTransaction(String transactionId,
//                                        String merchantId,
//                                        IntentRequest intentRequest);

    @Get(value = "/wallet")
    CryptoManagerResponse getWalletToBuyer(@Header String network,
                                           @Header String currency);


}
