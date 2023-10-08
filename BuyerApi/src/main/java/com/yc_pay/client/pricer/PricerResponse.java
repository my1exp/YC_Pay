package com.yc_pay.client.pricer;

import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;

@SerdeImport
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PricerResponse {
    private float amountCrypto;
}
