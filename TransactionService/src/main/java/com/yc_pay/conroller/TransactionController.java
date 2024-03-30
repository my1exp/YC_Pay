package com.yc_pay.conroller;

import com.yc_pay.model.Transaction;
import com.yc_pay.model.TransactionResponse;
import com.yc_pay.service.TransactionService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;


@Controller()
@Slf4j(topic = "TransactionController")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Get(uri = "/Transactions", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<ArrayList<TransactionResponse>> getUserTransaction(@Header String merchantId) {
        try {
            return HttpResponse.ok(transactionService.getUserTransaction(merchantId));
        } catch (Exception e) {
            return HttpResponse.badRequest();
        }
    }

    @Post(uri = "/identified", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
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

    @Post(uri = "/unidentified")
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
}
