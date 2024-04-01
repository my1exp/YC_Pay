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
public class TransactionToTxService {
    private String merchant_id;
    private String currency_crypto;
    private Double paid_amount_crypto;
    private Double required_amount_crypto;
    private String currency_fiat;
    private Double amount_fiat;
    private String hash;
}
