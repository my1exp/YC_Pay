package com.lk.model;

import io.micronaut.context.annotation.Bean;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable.Deserializable
@Serdeable.Serializable
@Introspected

public class Payout {

    private int payoutID;
    private String currencyFiat;
    private float amountFiat;
    private String addressTo;
    private String status;
    private LocalDate createdAt;
}
