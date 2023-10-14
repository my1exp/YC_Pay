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
public class IntentRequest {
    private String request_id;
    private String currency_crypto;
    private String network;
    private float amount_crypto;
    private float amount_fiat;
    private String currency_fiat;
    private String merchant_id;
}
