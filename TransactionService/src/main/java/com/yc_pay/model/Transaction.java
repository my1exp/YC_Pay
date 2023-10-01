package com.yc_pay.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Introspected
@SerdeImport
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Transaction {
    private String transactionId;
    private String merchantId;
    private String walletFrom;
    private String walletTo;
    private String currency;
    private BigDecimal amount;
    private String network;
    private LocalDateTime createdAt;
    private String category;
    private String status;
}
