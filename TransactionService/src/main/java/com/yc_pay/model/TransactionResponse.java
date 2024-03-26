package com.yc_pay.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;
import java.math.BigDecimal;



@Introspected
@SerdeImport
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionResponse {
    private String payment_id;
    private String currency_crypto;
    private BigDecimal amount_crypto;
    private String currency_fiat;
    private BigDecimal amount_fiat;
    private String payment_dttm;
}