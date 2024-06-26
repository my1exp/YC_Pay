package com.yc_pay.client.cryptoManager;

import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.Client;

@Client("http://crypto_manager:8082")
public interface CryptoManagerClient {

    @Get(value = "/wallet")
    CryptoManagerWalletResponse getWalletToBuyer(@Header String network,
                                                 @Header String currency,
                                                 @Header float amount_crypto,
                                                 @Header float amount_fiat,
                                                 @Header String merchant_id,
                                                 @Header String request_id,
                                                 @Header String session_id);
}
