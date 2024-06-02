package com.fodapi.controllers;

import com.fodapi.components.UserComponent;
import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.LikeRepository;
import com.fodapi.repository.UserRepository;
import com.fodapi.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InteractionController {

    @Autowired
    UserComponent userComponent;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikeRepository likeRepository;

    @PostMapping("/addLike")
    public ResponseEntity addLike(@RequestParam(value = "postId") String postId,
                        HttpServletRequest httpRequest) {

        String jwt = httpRequest.getHeader("jwt");
        if (!userComponent.isUserValid(jwtService.decodeJWT(jwt),
                userRepository.retrieveUserByToken(jwt))) {
            return new ResponseEntity("Single Post cannot be displayed! - jwt denied", HttpStatusCode.valueOf(500));
        }

        UsersEntity user = userRepository.retrieveUserByToken(jwt);

        if (likeRepository.retrieveLikePerPostAndId(postId, user.getId()).isEmpty()) {
            likeRepository.createLike(postId, user.getId());
            return new ResponseEntity("Created like!", HttpStatusCode.valueOf(200));
        }

        likeRepository.removeLike(postId, user.getId());
        return new ResponseEntity("Removed like!", HttpStatusCode.valueOf(200));
    }
}
