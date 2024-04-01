package com.yc_pay.Api;

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
public class CryptoCurrency {

    private String name;
    private BigDecimal price;
    private LocalDateTime dateTime;
}
