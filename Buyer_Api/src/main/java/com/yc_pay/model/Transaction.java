<<<<<<< HEAD
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
public class Transaction {
    private String transactionId;
    private String merchantId;
    private String walletFrom;
    private String walletTo;
    private String currency;
    private float amount;
    private String network;
    private String createdAt;
    private String category;
    private String status;
=======
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
public class Transaction {
    private String transactionId; // кто формирует id транзакции? Если каждый подключенный "магазин" будет формировать
    private String merchantId;
    private String walletFrom;
    private String walletTo;
    private String currency;
    private float amount;
    private String network;
    private String createdAt;
    private String category;
    private String status;
>>>>>>> 5cfbe8efbffc1fbe647e2209fbcbfc7e5cd1b38e
}