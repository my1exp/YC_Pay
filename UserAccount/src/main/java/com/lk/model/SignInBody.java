package com.lk.model;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInBody {
    private String email;
    private String password;

}
