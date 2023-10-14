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
public class IntentToTransaction {
//    session_id,
//    request_Id,

    private String wallet_to;
    private String currency;
    private String network;
    private float amount;
    private String status;
}
