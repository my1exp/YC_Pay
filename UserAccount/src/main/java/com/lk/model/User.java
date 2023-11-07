package com.lk.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable.Deserializable
@Serdeable.Serializable
@Introspected
public class User {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;


}
