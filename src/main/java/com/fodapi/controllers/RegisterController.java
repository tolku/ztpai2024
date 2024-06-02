package com.fodapi.controllers;

import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.UserRepository;
import com.fodapi.services.BCryptService;
import com.fodapi.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UsersEntity userToBeRegistered) {
        if (userToBeRegistered.getEmail() == null || userToBeRegistered.getPassword() == null
                || userToBeRegistered.getName() == null
                || userRepository.retrieveUserByEmail(userToBeRegistered.getEmail()) != null) {
            return new ResponseEntity<>("Cannot register!", HttpStatusCode.valueOf(200));
        }

        userRepository.createNewUser(userToBeRegistered.getName(),
                userToBeRegistered.getEmail(),
                BCryptService.hashpw(userToBeRegistered.getPassword(), BCryptService.gensalt()));

        emailService.sendRegistrationConfirmationEmail(userToBeRegistered.getEmail());

        return new ResponseEntity("User created, log in", HttpStatusCode.valueOf(200));

    }
}
