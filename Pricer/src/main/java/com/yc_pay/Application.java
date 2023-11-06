package com.yc_pay;

import com.yc_pay.Api.CmcThread;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
        CmcThread cmcThread = new CmcThread();
        cmcThread.start();
    }
}