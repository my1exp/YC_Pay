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
public class PaymentExists {
    private Integer paymentId;
    private Double requiredAmount;
    private Double paidAmount;
    private Integer walletId;
    private String IntentRequestId;
}
