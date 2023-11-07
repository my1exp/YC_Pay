package com.lk.model;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable.Deserializable
@Introspected
public class SignUpBody {

    private String email;
    private String password;
    private String confirmPassword;

}
