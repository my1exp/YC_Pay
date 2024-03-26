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
    private String payment_id;
    private String merchant_id;
    private String wallet_from;
    private String wallet_to;
    private String currency_crypto;
    private BigDecimal amount_crypto;
    private String network;
    private LocalDateTime payment_dttm;
    private String category;
    private String currency_fiat;
    private BigDecimal amount_fiat;
    private String income_pocket;
    private String output_pocket;
}
