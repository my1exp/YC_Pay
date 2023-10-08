package com.yc_pay.client.cryptoManager;

import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;

@SerdeImport
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CryptoManagerResponse {
    private String wallet;
}
