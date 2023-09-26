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
public class TransactionRequest {
    private String hash;
    private String walletFrom;
    private String walletTo;
    private String currency;
    private float amount;
    private String network;
    private String createdAt;
}
