package com.lk.service;

import com.lk.DataBase;
import com.lk.model.Payout;
import com.lk.model.Payouts;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import java.time.LocalDate;
import static com.lk.model.dbModels.generated.Tables.PAYOUTS;



@Singleton
@AllArgsConstructor
public class PayoutService {

    private final DataBase dataBase;

    public Payouts getPayuots() {
        DSLContext getPayoutsInfo = dataBase.connectionDataBase();
        Payouts payouts = new Payouts();

//        BigDecimal sumValue = (BigDecimal) getPayoutsInfo.select(sum(PAYOUTS.AMOUNT))
//                .from(PAYOUTS)
//                .fetch();
//        float round = sumValue.setScale(2, RoundingMode.FLOOR).floatValue();
        Result<Record> payoutsRecord = getPayoutsInfo.select()
                .from(PAYOUTS)
                .fetch();

        for (Record r:
             payoutsRecord) {
            payouts.payoutAdd(new Payout(
                    r.get(PAYOUTS.PAYOUT_ID),
                    r.get(PAYOUTS.CURRENCY),
                    r.get(PAYOUTS.AMOUNT),
                    r.get(PAYOUTS.ADDRESS_TO),
                    r.get(PAYOUTS.STATUS),
                    r.get(PAYOUTS.CREATED_AT))
            );
        }

//        payouts.setBalance(round);

        return payouts;
    }

    public void createPayout(int id, float amountFiat, String addressTo) {
        DSLContext postUser = dataBase.connectionDataBase();

        LocalDate date = LocalDate.now();
        postUser.insertInto(PAYOUTS, PAYOUTS.PAYOUT_ID,
                        PAYOUTS.CURRENCY, PAYOUTS.AMOUNT,
                        PAYOUTS.ADDRESS_TO,  PAYOUTS.STATUS, PAYOUTS.CREATED_AT)
                .values(id, "USD", amountFiat, addressTo, "waiting", date)
                .execute();




    }
}
