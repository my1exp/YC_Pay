package com.yc_pay.conroller;

import com.yc_pay.model.Transaction;
import com.yc_pay.service.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Controller()
@Slf4j(topic = "TransactionController")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Post(uri = "/transaction/identified")
    public HttpResponse<String> identifiedTransaction(@Header double paid_amount_crypto,
                                                      @Header String currency_crypto,
                                                      @Header double required_amount_crypto,
                                                      @Header String currency_fiat,
                                                      @Header double amount_fiat,
                                                      @Header String merchant_id) {
        try {
            Transaction transaction = new Transaction(merchant_id, currency_crypto, paid_amount_crypto,
                    required_amount_crypto, currency_fiat, amount_fiat);
            transactionService.createEntriesForIdentifiedTransaction(transaction);
            return HttpResponse.ok();
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }

    @Post(uri = "/transaction/unidentified")
    public HttpResponse<String> unidentifiedTransaction(@Header double paid_amount_crypto,
                                                        @Header String currency_crypto) {
        try {
            System.out.println(paid_amount_crypto);
            transactionService.createEntriesForUnidentifiedTransaction(paid_amount_crypto, currency_crypto);
            return HttpResponse.ok();
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }

    @Post(uri = "/transaction/exchange")
    public HttpResponse<String> identifiedTransactionExchange(@Header String exchanged_currency_crypto,
                                                              @Header double exchanged_amount_crypto,
                                                              @Header String received_currency_fiat,
                                                              @Header double received_amount_fiat,
                                                              @Header Integer identification_flag) {
        try{
            transactionService.createEntriesForExchangeTransaction(exchanged_amount_crypto, exchanged_currency_crypto,
                    received_currency_fiat, received_amount_fiat, identification_flag);
            return HttpResponse.ok();
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }

    @Post(uri = "/transaction/payment")
    public HttpResponse<String> identifiedTransactionExchange(@Header String merchant_id,
                                                              @Header String currency_fiat,
                                                              @Header double amount_fiat) {

        try{
            String result = transactionService.createEntriesForPaymentTransaction(merchant_id, currency_fiat, amount_fiat);
            if (result.equals("Ok")) {
                return HttpResponse.ok(result);
            }else{
                return HttpResponse.badRequest(result);
            }
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }
}
