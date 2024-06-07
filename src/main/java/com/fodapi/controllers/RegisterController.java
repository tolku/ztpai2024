package com.fodapi.controllers;

import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.AuthorityRepository;
import com.fodapi.repository.UserRepository;
import com.fodapi.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity register(@RequestBody UsersEntity userToBeRegistered) {
        if (userToBeRegistered.getEmail() == null || userToBeRegistered.getPassword() == null
                || userRepository.retrieveUserByEmail(userToBeRegistered.getEmail()) != null) {
            return new ResponseEntity<>("Cannot register!", HttpStatusCode.valueOf(500));
        }

        userRepository.createNewUser("imie",
                userToBeRegistered.getEmail(),
                passwordEncoder.encode(userToBeRegistered.getPassword()));

        authorityRepository.addWriteAuthorityToUser(userToBeRegistered.getEmail());

        emailService.sendRegistrationConfirmationEmail(userToBeRegistered.getEmail());

        return new ResponseEntity("User created, log in", HttpStatusCode.valueOf(200));
    }
}
