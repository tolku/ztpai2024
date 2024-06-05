package com.fodapi.controllers;

import com.fodapi.components.UserComponent;
import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.UserRepository;
import com.fodapi.services.BCryptService;
import com.fodapi.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PasswordManagingController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserComponent userComponent;

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody UsersEntity user) {
        userRepository.updateUserPassword(SecurityContextHolder.getContext().getAuthentication().getName(),
                BCryptService.hashpw(user.getPassword(), BCryptService.gensalt()));

        return new ResponseEntity("Password changed successfully, sign in!", HttpStatusCode.valueOf(200));
    }
}
