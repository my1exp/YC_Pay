package com.yc_pay.client.xrp;

import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.*;

@Client("http://localhost:8080")
public interface XrpClient {

//    @Get(value = "/wallet")
//    XrpWalletResponse getWallet(@Body String network,
//                                @Body String currency);


//    @Put(value = "/update_intent")
//    void deliveryToPayment(@Header String requestId, @Header String sessionId);
}
