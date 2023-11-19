package com.lk.service;

import com.lk.model.Orders;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class SalesService {

    public Orders getOrders(int id){
        return new Orders();
    }

}
