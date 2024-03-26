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
public class MerchantPayment {
    private String merchant_id;
    private String request_id;
    private String currency_crypto;
    private BigDecimal amount_crypto;
    private String currency_fiat;
    private BigDecimal amount_fiat;
    private LocalDateTime dttm;
}
