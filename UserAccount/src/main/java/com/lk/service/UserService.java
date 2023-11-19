package com.lk.service;

import com.lk.DataBase;
import com.lk.model.User;
import com.lk.model.dbModels.generated.tables.records.UsersRecord;
import io.micronaut.http.annotation.Body;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;


import static com.lk.model.dbModels.generated.Tables.*;
import static org.jooq.impl.DSL.row;

@Singleton
@AllArgsConstructor
public class UserService {

    private final DataBase dataBase;

    public void createUser(String email, String password){
        DSLContext postUser = dataBase.connectionDataBase();
        postUser.insertInto(USERS, USERS.EMAIL, USERS.PASSWORD)
                .values(email, password)
                .execute();
    }

    public void updateUserInfo(int id, @Body User user){
        DSLContext putUser = dataBase.connectionDataBase();
        putUser.update(USERS)
                .set(row(USERS.EMAIL, USERS.FIRST_NAME, USERS.LAST_NAME, USERS.PHONE),
                    row(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPhone()))
                .where(USERS.ID.eq(id))
                .execute();
    }


    public User getUserInfo(int id) {
        DSLContext getUserInfo = dataBase.connectionDataBase();
        UsersRecord user = getUserInfo.fetchOne(USERS, USERS.ID.eq(id));
        return new User(id,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword());
    }
}
