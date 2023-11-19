package com.lk.model;

import lombok.*;
import org.jooq.Record;
import org.jooq.Result;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payouts {

    private ArrayList<Payout> payouts;
    private float balance;


    public void payoutAdd(Payout payout){
        payouts.add(payout);
    }




}
