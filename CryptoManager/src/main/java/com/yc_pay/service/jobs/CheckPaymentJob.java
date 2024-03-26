package com.yc_pay.service.jobs;

import com.yc_pay.model.PaymentExists;
import com.yc_pay.service.BlockChairAPI;
import com.yc_pay.service.DatabaseService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_PAYMENTS;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_WALLETS;
import static java.lang.Math.pow;


@Singleton
@Slf4j(topic = "CheckPaymentJob")
public class CheckPaymentJob extends Thread {
    ApplicationContext applicationContext = ApplicationContext.run();
    Environment environment = applicationContext.getEnvironment();

    public void run() {

        while (true) {

            String url = environment.getProperty("db.url", String.class).get();
            String userName = environment.getProperty("db.username", String.class).get();
            String password = environment.getProperty("db.password", String.class).get();

            try (Connection conn = DriverManager.getConnection(url, userName, password)) {
                DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
                @NotNull Result<Record3<String, String, Integer>> result = dslContext.
                        select(CRYPTO_WALLETS.CURRENCY_CRYPTO, CRYPTO_WALLETS.ADDRESS, CRYPTO_WALLETS.ID)
                        .distinctOn(CRYPTO_WALLETS.ADDRESS)
                        .from(CRYPTO_PAYMENTS)
                        .leftJoin(CRYPTO_WALLETS).on(CRYPTO_WALLETS.ID.eq(CRYPTO_PAYMENTS.CRYPTO_WALLET_ID))
                        .where(CRYPTO_PAYMENTS.STATUS.eq(1))
                        .fetch();
                conn.close();

                if (!result.isEmpty()) {

                    HashMap<Integer, String> walletAddressMap = new HashMap<>();
                    ArrayList<Integer> successPayments = new ArrayList<>();

                    //Записываем адреса кошельков
                    for (Record r : result) {
                        walletAddressMap.put(r.getValue(CRYPTO_WALLETS.ID), r.getValue(CRYPTO_WALLETS.ADDRESS));
                    }
                    //Запрашиваем по каждому адресу кошелька транзакции
                    for (Integer walletId : walletAddressMap.keySet()) {
                        String walletAddress = walletAddressMap.get(walletId);
                        String response = BlockChairAPI.makeAPICall(walletAddress);
                        JSONObject obj = new JSONObject(response);
                        String response_code = obj.getJSONObject("context").get("code").toString();

                        //Если код ответа 200, то аккаунт создан
                        if (response_code.equals("200")) {
                            JSONArray transactions = obj.getJSONObject("data").getJSONObject(walletAddress).
                            getJSONObject("transactions").getJSONArray("transactions");

                            //Проверяем по каждой транзакции hash
                            for (int i = 0; i < transactions.length(); i++) {
                                JSONObject transaction = transactions.getJSONObject(i).getJSONObject("tx");
                                String hash = transaction.get("hash").toString();

                                Boolean isHashExists = DatabaseService.isHashExists(walletId, hash);
                                String destination = transaction.get("Destination").toString();

                                if (!isHashExists && destination.equals(walletAddress)) {
                                    Double received_amount = Double.parseDouble(transaction.get("Amount").toString()) / pow(10, 6);
                                    try {
                                        //Если destination_tag не пустой, то пробуем идентифицировать транзакцию
                                        if(transaction.get("DestinationTag") != null) {
                                            Integer destinationTag = Integer.parseInt(transaction.get("DestinationTag").toString());
                                            //проверка оплаты в базе по destination_tag и адресу кошелька
                                            PaymentExists paymentInfo = DatabaseService.isPaymentExist(destinationTag, walletAddress);

                                            if (paymentInfo == null) {
                                                // Если оплата не найдена, то
                                                DatabaseService.addNewTransactionHash(walletId, hash, null, received_amount);
                                                // ToDO создаем проводки в сервисе транзакций, относим на расходы с плюсом

                                            }else if(paymentInfo.getPaidAmount() != null &&
                                                    paymentInfo.getRequiredAmount() <= paymentInfo.getPaidAmount() + received_amount) {
                                                // Если payment найден и
                                                // необходимая к оплате сумма меньше или равна (текущей + ранее полученных оплат), то
                                                DatabaseService.addNewTransactionHash(walletId, hash, paymentInfo.getPaymentId(), received_amount);
                                                DatabaseService.updateOfPaymentPaidAmountAndStatus(received_amount +
                                                        paymentInfo.getPaidAmount(), 2, paymentInfo.getPaymentId());
                                                successPayments.add(paymentInfo.getPaymentId());
                                                // ToDO создаем проводки в сервисе транзакций
                                            }else if(paymentInfo.getPaidAmount() == null &&
                                                    paymentInfo.getRequiredAmount() <= received_amount) {
                                                // Если payment найден и необходимая к оплате сумма меньше или равна текущей
                                                // и ранее полученных оплат не было, то
                                                DatabaseService.addNewTransactionHash(walletId, hash, paymentInfo.getPaymentId(), received_amount);
                                                DatabaseService.updateOfPaymentPaidAmountAndStatus(received_amount,
                                                        2, paymentInfo.getPaymentId());
                                                successPayments.add(paymentInfo.getPaymentId());
                                                // ToDO создаем проводки в сервисе транзакций
                                            }else if(paymentInfo.getPaidAmount() != null &&
                                                    paymentInfo.getRequiredAmount() > received_amount) {
                                                // Если payment найден и
                                                // необходимая к оплате сумма больше (текущей + ранее полученных оплат), то
                                                DatabaseService.addNewTransactionHash(walletId, hash, paymentInfo.getPaymentId(), received_amount);
                                                DatabaseService.updateOfPaymentPaidAmountAndStatus(received_amount +
                                                        paymentInfo.getPaidAmount(), 1, paymentInfo.getPaymentId());
                                                // ToDO создаем проводки в сервисе транзакций
                                            }else {
                                                // Если payment найден и
                                                // необходимая к оплате сумма больше текущей и ранее полученных оплат не было, то
                                                DatabaseService.addNewTransactionHash(walletId, hash, paymentInfo.getPaymentId(), received_amount);
                                                DatabaseService.updateOfPaymentPaidAmountAndStatus(received_amount,
                                                        1, paymentInfo.getPaymentId());
                                                // ToDO создаем проводки в сервисе транзакций
                                            }
                                        }
                                    }catch (Exception e) {
                                        DatabaseService.addNewTransactionHash(walletId, hash, null, received_amount);
                                        //ToDO учитываем оплату как прибыль на комиссиях
                                    }
                                }
                            }
                        }
                    }
                    // Обновляем статус доставки в PaymentApi
                    for (Integer paymentId : successPayments) {
                        DatabaseService.updateDeliveredFlag(paymentId, 0);
                    }
                    log.info("checkPaymentJob executed");
                    Thread.sleep(1000 * 60 * 2); // 2 minutes
                }
                log.info("checkPaymentJob executed");
                Thread.sleep(1000 * 60 * 2); // 2 minutes
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
