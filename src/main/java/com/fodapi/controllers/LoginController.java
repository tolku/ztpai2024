package com.fodapi.controllers;

import com.fodapi.services.JwtService;
import com.fodapi.components.UserComponent;
import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @Autowired
    Environment env;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    @Transactional
    protected ResponseEntity<String> login(@RequestBody UsersEntity user) {
        UsersEntity userFromDb = userRepository.retrieveUserByEmail(user.getEmail());

        if (userFromDb == null || !UserComponent.isCredentialsValid(user, userFromDb)) {
            return new ResponseEntity<>("Credentials mismatch!", HttpStatusCode.valueOf(301));
        }

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        String jwtToken = jwtService.createJWT(env.getProperty("jwt.issuer"), userFromDb.getEmail());
        multiValueMap.add("jwt", jwtToken);

        userRepository.updateUserTokenAndExpDate(jwtToken, env.getProperty("jwt.expiration_time"));

        return new ResponseEntity<>(multiValueMap, HttpStatusCode.valueOf(200));
    }

}
