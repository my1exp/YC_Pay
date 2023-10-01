package com.yc_pay.service;

import com.yc_pay.model.ExchangeResponse;
import jakarta.inject.Singleton;

@Singleton
public class ExchangeRequestService {

    public ExchangeResponse returnAmountFiat(String currencyCrypto,
                                             float amountCrypto,
                                             String currencyFiat){
        //ToDo должен ходить в базу и смотреть есть ли такие currencyCrypto и currencyFiat
        //ToDo написать корректную логику запроса в другой сервис перевода крипты в фиат
        return new ExchangeResponse(currencyCrypto, amountCrypto, currencyFiat, 0);
    }
}
