package com.yc_pay.service.jobs;

import com.yc_pay.client.xrp.XrpCheckResponse;
import com.yc_pay.client.xrp.XrpClient;
import com.yc_pay.model.DestinationTag;
import com.yc_pay.model.WalletAddress;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_PAYMENTS;
import static com.yc_pay.model.dbModels.generated.Tables.CRYPTO_WALLETS;
import static org.jooq.impl.DSL.listAgg;


@Singleton
public class CheckPaymentJob {
//
//    private final XrpClient xrpClient;
//
//    ApplicationContext applicationContext = ApplicationContext.run();
//    Environment environment = applicationContext.getEnvironment();
//
//    public CheckPaymentJob(XrpClient xrpClient) {
//        this.xrpClient = xrpClient;
//    }
//
//    @Scheduled(fixedDelay = "10s", initialDelay = "5s")
//    void execute(){
//
//        String url = environment.getProperty("db.url", String.class).get();
//        String userName = environment.getProperty("db.username", String.class).get();
//        String password =  environment.getProperty("db.password", String.class).get();
//
//        try (Connection conn = DriverManager.getConnection(url,userName,password)) {
//            DSLContext dslContext = DSL.using(conn, SQLDialect.POSTGRES);
//            @NotNull Result<Record3<String, String, String>> result = dslContext.
//                    select(CRYPTO_WALLETS.CURRENCY_CRYPTO, CRYPTO_WALLETS.ADDRESS,
//                            listAgg(CRYPTO_PAYMENTS.DESTINATION_TAG, ",")
//                                    .withinGroupOrderBy(CRYPTO_PAYMENTS.DESTINATION_TAG))
//                    .from(CRYPTO_PAYMENTS)
//                    .leftJoin(CRYPTO_WALLETS).on(CRYPTO_WALLETS.ID.eq(CRYPTO_PAYMENTS.CRYPTO_WALLET_ID))
//                    .where(CRYPTO_PAYMENTS.STATUS.eq(1))
//                    .groupBy(CRYPTO_WALLETS.ADDRESS, CRYPTO_WALLETS.CURRENCY_CRYPTO)
//                    .fetch();
//            conn.close();
//
//            System.out.println(result);
//
//            if(!result.isEmpty()){
//                ArrayList<WalletAddress> walletAddresses = new ArrayList<>();
//                HashMap<String, ArrayList<WalletAddress>> payments = new HashMap<>();
//
//                for (Record r : result) {
//                    ArrayList<DestinationTag> destinationTags = new ArrayList<>();
//                    String stringTags = r.getValue(listAgg(CRYPTO_PAYMENTS.DESTINATION_TAG, ",")
//                            .withinGroupOrderBy(CRYPTO_PAYMENTS.DESTINATION_TAG));
//                    String[] tags = stringTags.split(",");
//                    for (String tag : tags) {
//                        DestinationTag destinationTag = new DestinationTag(Integer.parseInt(tag));
//                        destinationTags.add(destinationTag);
//                    }
//                    WalletAddress walletAddress = new WalletAddress(r.getValue(CRYPTO_WALLETS.ADDRESS), destinationTags);
//                    walletAddresses.add(walletAddress);
//
//                    payments.put(r.getValue(CRYPTO_WALLETS.CURRENCY_CRYPTO), walletAddresses);
//                }
//                System.out.println(payments);
//
//                XrpCheckResponse xrpCheckResponse = xrpClient.getPayments(payments);
//
//                System.out.println(xrpCheckResponse);
//
//            }
//
//                //Собрать список и передать в сервис BlockChainService
//                //Получить ответ из BlockChainService
//                //Обновить статус = 2 в таблице payment (для тех, по которым пришла оплата)
//
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
