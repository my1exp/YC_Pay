package com.lk.controller;

import com.lk.model.Orders;
import com.lk.service.SalesService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/Sales")
public class SalesController {

    @Inject
    SalesService salesService;

    @Get("/id")
    public Orders getOrders(int id){
        return salesService.getOrders(id);
    }
}
