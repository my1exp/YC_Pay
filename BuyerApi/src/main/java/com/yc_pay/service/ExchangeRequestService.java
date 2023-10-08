package com.yc_pay.service;

import com.yc_pay.client.pricer.PricerClient;
import com.yc_pay.model.ExchangeResponse;
import jakarta.inject.Singleton;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Singleton
public class ExchangeRequestService {

    private final PricerClient pricerClient;

    public ExchangeRequestService(PricerClient pricerClient) {
        this.pricerClient = pricerClient;
    }

    public ExchangeResponse returnAmountFiat(String currencyCrypto,
                                             float amountFiat,
                                             String currencyFiat){
        //ToDo должен ходить в базу и смотреть есть ли такие currencyCrypto и currencyFiat ??
        // как он сможет выбрать что-то другое, если мы ему отдаем конкретный список
        // Если только проверка на дурака

        float amountCrypto = pricerClient.getPriceToBuyer(currencyCrypto, amountFiat, currencyFiat).getAmountCrypto();
        amountCrypto = (float) (amountCrypto * 1.025);
        MathContext context = new MathContext(5, RoundingMode.HALF_UP);
        BigDecimal formattedAmountCrypto = new BigDecimal(amountCrypto, context);

        return new ExchangeResponse(currencyCrypto,formattedAmountCrypto.doubleValue(), currencyFiat, amountFiat);
    }
}
