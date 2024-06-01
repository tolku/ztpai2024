package com.fodapi.components;

import com.fodapi.services.BCryptService;
import com.fodapi.entity.UsersEntity;

import java.util.Objects;

public class UserComponent {

    public static boolean isCredentialsValid(UsersEntity userFromRequest, UsersEntity userFromDb) {
        return Objects.equals(userFromRequest.getEmail(), userFromDb.getEmail())
                && BCryptService.checkpw(userFromRequest.getPassword(), userFromDb.getPassword());
    }

}
