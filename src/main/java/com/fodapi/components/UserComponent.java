package com.fodapi.components;

import com.fodapi.services.BCryptService;
import com.fodapi.entity.UsersEntity;
import com.fodapi.services.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserComponent {

    @Autowired
    private Environment env;

    @Autowired
    private JwtService jwtService;

    public boolean isCredentialsValid(UsersEntity userFromRequest, UsersEntity userFromDb) {
        return Objects.equals(userFromRequest.getEmail(), userFromDb.getEmail())
                && BCryptService.checkpw(userFromRequest.getPassword(), userFromDb.getPassword());
    }

    public boolean isUserValid(Claims claims, UsersEntity user) {
        return Objects.equals(user.getEmail(), claims.getSubject())
                && user.getCookieExpirationDate().toInstant().getEpochSecond() == claims.getExpiration().toInstant().getEpochSecond() //not sure if comparing timestamp with date will work?
                && Objects.equals(claims.getIssuer(), env.getProperty("jwt.issuer"));
    }

}
