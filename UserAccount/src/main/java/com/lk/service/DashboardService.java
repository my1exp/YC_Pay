package com.lk.service;

import com.lk.model.Dashboard;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;


@Singleton
@AllArgsConstructor
public class DashboardService {


    public Dashboard getDashboard(int id){
        return new Dashboard(1000, 100, 10, 1);
    }
}
