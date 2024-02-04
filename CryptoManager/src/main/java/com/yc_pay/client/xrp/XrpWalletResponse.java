package com.yc_pay.client.xrp;

import io.micronaut.serde.annotation.SerdeImport;
import lombok.*;

@SerdeImport
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XrpWalletResponse {
    private String address;
    private String private_key;
    private String public_key;
}
