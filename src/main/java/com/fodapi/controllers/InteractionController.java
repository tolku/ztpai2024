package com.fodapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fodapi.components.UserComponent;
import com.fodapi.entity.CommentsEntity;
import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.CommentRepository;
import com.fodapi.repository.LikeRepository;
import com.fodapi.repository.UserRepository;
import com.fodapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/addLike")
    public ResponseEntity addLike(@RequestParam(value = "postId") String postId) {

        UsersEntity user = userRepository.retrieveUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (likeRepository.retrieveLikePerPostAndId(postId, user.getId()).isEmpty()) {
            likeRepository.createLike(postId, user.getId());
            return new ResponseEntity("Created like!", HttpStatusCode.valueOf(200));
        }

        likeRepository.removeLike(postId, user.getId());
        return new ResponseEntity("Removed like!", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/addComment")
    public ResponseEntity addComment(@RequestParam(value = "postId") String postId, @RequestBody Map<String, String> requestBody) {

        UsersEntity user = userRepository.retrieveUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        commentRepository.addComment(requestBody.getOrDefault("comment", null), postId, user.getId());
        return new ResponseEntity("Comment added!", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getComments")
    public ResponseEntity getComemnts(@RequestParam(value = "postId") String postId) throws JsonProcessingException {
        List<CommentsEntity> comments = commentRepository.getComments(postId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(comments);

        return new ResponseEntity(json, HttpStatusCode.valueOf(200));
    }
}
