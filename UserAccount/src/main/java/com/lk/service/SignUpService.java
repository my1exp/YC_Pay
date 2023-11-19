package com.lk.service;

import com.lk.model.User;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Singleton;

import java.net.URI;

@Singleton
public class SignUpService {


    private final UserService userService;

    public SignUpService(UserService userService) {
        this.userService = userService;
    }

    public void signUpUser(String email, String password, String confirmPassword) {
        if (password.equals(confirmPassword)){
            userService.createUser(email, password);
        } else {
            // TODO: 28.10.2023 прокидывать ошибку
            System.out.println("ошибка");
        }

    }
}
