package com.yc_pay.client;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;

@SerdeImport
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String transactionId;
    private String merchantId;
    private String walletFrom;
    private String walletTo;
    private String currency;
    private float amount;
    private String network;
    private String createdAt;
    private String category;
    private String status;
}
