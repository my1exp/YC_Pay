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
    private String currency;
    private String network;
    private float amount;
    private String merchant_id;
}
