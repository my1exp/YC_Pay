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
public class WalletRequestDB {
    private String network;
    private String currency;
    private String wallet;
    private String status;
    private int walletId;
}