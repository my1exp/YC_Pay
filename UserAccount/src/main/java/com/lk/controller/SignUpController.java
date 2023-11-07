package com.lk.controller;

import com.lk.model.SignUpBody;
import com.lk.service.SignUpService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller()
public class SignUpController {
    @Inject
    SignUpService signUpService;

    @Post(uri = "/Sign_up", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public void createUser(@Body SignUpBody signUpBody){
        signUpService.signUpUser(signUpBody.getEmail(),
                                signUpBody.getPassword(),
                                signUpBody.getConfirmPassword());

    }

}