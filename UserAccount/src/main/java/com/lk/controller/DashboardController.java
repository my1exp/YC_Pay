package com.lk.controller;

import com.lk.model.Dashboard;
import com.lk.service.DashboardService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/Dashboard")
public class DashboardController {

    @Inject
    DashboardService dashboardService;

    @Get(uri = "/{id}", produces = MediaType.APPLICATION_JSON)
    public Dashboard getDashboard(int id){
        return dashboardService.getDashboard(id);
    }

}
