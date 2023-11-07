package com.lk.model;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable.Deserializable
@Serdeable.Serializable
@Introspected
public class Order {

    private String idempotencyKey;
    private String orderId;
    private String currencyFiat;
    private String amountFiat;
    private String payCurrency;
    private String createdAt;

}
