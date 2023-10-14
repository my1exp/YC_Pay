package com.yc_pay.controller;

import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
import com.yc_pay.service.IntentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;

@Controller()
public class IntentController {

    private final IntentService intentService;

    public IntentController(IntentService intentService) {
        this.intentService = intentService;
    }

    @Get(uri = "/intent")
    public MutableHttpResponse<IntentResponse> getBuyerIntent(@Header String session_id,
                                                              @Header String request_id){
        try {
            return HttpResponse.ok(intentService.getIntent(session_id, request_id));
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }

    @Put(uri = "/intent", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<String> postBuyerIntent(@Header String session_id,
                                                @Body IntentRequest intentRequest)
    {
        try {
            intentService.postBuyerIntent(session_id, intentRequest);
            return HttpResponse.ok();
        }catch (Exception e){
            return HttpResponse.accepted();
        }
    }
}