package com.yc_pay.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;

@Introspected
@SerdeImport
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Intent {
    private String network;
    private String currency;
    private float amountCrypto;
    private float amountFiat;
    private String merchant_id;
    private String request_id;
    private String session_id;
}
