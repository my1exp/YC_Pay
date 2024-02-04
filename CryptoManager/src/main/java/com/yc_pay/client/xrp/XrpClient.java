package com.yc_pay.client.xrp;

import com.yc_pay.model.WalletAddress;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Client( value = "http://localhost:9090")
public interface XrpClient {

    @Get(value = "/wallet")
    XrpWalletResponse getWallet(@Body String network,
                                @Body String currency);


    @Get(value = "/payments")
    XrpCheckResponse getPayments(@Body HashMap<String, ArrayList<WalletAddress>> payments);
}
