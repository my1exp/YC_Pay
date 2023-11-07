package com.lk.controller;

import com.lk.model.User;
import com.lk.service.UserService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

@Controller("/user")
public class UserController {
    @Inject
    UserService userService;

    @Put("/update/{id}")
    public void updateUserInfo(int id, @Body User update){
        userService.updateUserInfo(id, update);
    }
//    public HttpResponse<User> createUser(@Body User user){
//        return HttpResponse. (signUpService.signUpUser(signUpBody));
//    }

    @Get(uri = "/info/{id}", produces = MediaType.APPLICATION_JSON)
    public User getUserInfo(int id){
        return userService.getUserInfo(id);
    }

}
