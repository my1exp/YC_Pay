package com.yc_pay.controller;

import com.yc_pay.model.IntentRequest;
import com.yc_pay.model.IntentResponse;
import com.yc_pay.model.IntentStatusResponse;
import com.yc_pay.service.IntentService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

@Controller()
public class IntentController {

    private final IntentService intentService;

    public IntentController(IntentService intentService) {
        this.intentService = intentService;
    }

    @Get(uri = "/intent")
    public HttpResponse<IntentResponse> getBuyerIntent(@Header String session_id,
                                                       @Header String order_id,
                                                       @Header String merchant_id){
        try {
            IntentResponse intentResponse = intentService.getIntent(session_id, order_id, merchant_id);
            if(intentResponse.getNetwork() != null) {
                return HttpResponse.ok(intentResponse);
            }else {
                return HttpResponse.notFound();
            }
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }

    @Put(uri = "/intent", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<String> postBuyerIntent(@Header String session_id,
                                                @Body IntentRequest intentRequest)
    {
        try {
            if(intentService.postBuyerIntent(session_id, intentRequest).equals("!Unique intent")){
                return HttpResponse.accepted();
            }else if (intentService.postBuyerIntent(session_id, intentRequest).equals("Difference check false")){
                return HttpResponse.badRequest("ERROR: Difference check false");
            }else{
                return HttpResponse.ok();
            }
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }

    @Get(uri = "/intent_status")
    public HttpResponse<IntentStatusResponse> getBuyerIntentStatus(@Header String session_id,
                                                                   @Header String order_id,
                                                                   @Header String merchant_id){
        try {
            IntentStatusResponse intentStatusResponse = intentService.getIntentStatus(session_id, order_id, merchant_id);
            if(intentStatusResponse.getStatus() != null){
                return HttpResponse.ok(intentStatusResponse);
            }else{
                return HttpResponse.badRequest();
            }
        }catch (Exception e){
            return HttpResponse.badRequest();
        }
    }
}