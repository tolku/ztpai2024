package com.fodapi.controllers;

import com.fodapi.components.UserComponent;
import com.fodapi.entity.PostContentsEntity;
import com.fodapi.entity.PostTitlesEntity;
import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.PostContentRepository;
import com.fodapi.repository.PostTitleRepository;
import com.fodapi.repository.UserRepository;
import com.fodapi.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Controller
public class PostController {

    @Autowired
    UserComponent userComponent;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostTitleRepository postTitleRepository;

    @Autowired
    PostContentRepository postContentRepository;

    @PostMapping("/addPost")
    public ResponseEntity addPost(@RequestBody Map<String, String> requestBody,
                                  HttpServletRequest httpRequest) {
        PostTitlesEntity postTitle = new PostTitlesEntity();
        postTitle.setTopic(requestBody.get("topic"));
        PostContentsEntity postContent = new PostContentsEntity();
        postContent.setContent(requestBody.get("content"));
        postContent.setImagePath(requestBody.getOrDefault("image_path", null));

        String jwt = httpRequest.getHeader("jwt");
        if (!userComponent.isUserValid(jwtService.decodeJWT(jwt),
                userRepository.retrieveUserByToken(jwt))) {
            return new ResponseEntity("Password cannot be changed!", HttpStatusCode.valueOf(500));
        }

        UsersEntity user = userRepository.retrieveUserByToken(jwt);

        if (!isPostValid(postTitle, postContent)) {
            return new ResponseEntity("Post is not valid!", HttpStatusCode.valueOf(500));
        }

        Timestamp creationDate = Timestamp.valueOf(LocalDateTime.now());

        postTitleRepository.createPostTitle(postTitle.getTopic(), user.getId(), creationDate);

        String postId = postTitleRepository.retrievePostId(user.getId(), postTitle.getTopic(), creationDate);

        postContentRepository.createPostContent(postContent.getContent(), postId, postContent.getImagePath());

        return new ResponseEntity("Post created!", HttpStatusCode.valueOf(200));
    }

    @PostMapping("/editPost")
    public ResponseEntity editPost(@RequestBody Map<String, String> requestBody,
                                   HttpServletRequest httpRequest) {
        PostTitlesEntity postTitle = new PostTitlesEntity();
        postTitle.setTopic(requestBody.get("topic"));
        postTitle.setId(requestBody.get("postId"));
        PostContentsEntity postContent = new PostContentsEntity();
        postContent.setContent(requestBody.get("content"));
        postContent.setImagePath(requestBody.getOrDefault("image_path", null));

        String jwt = httpRequest.getHeader("jwt");
        if (!userComponent.isUserValid(jwtService.decodeJWT(jwt),
                userRepository.retrieveUserByToken(jwt))) {
            return new ResponseEntity("Password cannot be changed!", HttpStatusCode.valueOf(500));
        }

        if (!isPostValid(postTitle, postContent) || postTitle.getId() == null) {
            return new ResponseEntity("Post is not valid!", HttpStatusCode.valueOf(500));
        }

        UsersEntity user = userRepository.retrieveUserByToken(jwt);

        if (!Objects.equals(postTitleRepository.retrievePostTitleUserById(postTitle.getId()), user.getId())) {
            return new ResponseEntity("You can only edit your posts!", HttpStatusCode.valueOf(500));
        }

        postTitleRepository.updatePostTitle(postTitle.getTopic(), postTitle.getId());

        postContentRepository.updatePostContentById(postTitle.getId(),
                postContent.getContent(),
                postContent.getImagePath());

        return new ResponseEntity("Post edited!", HttpStatusCode.valueOf(200));

    }

    @GetMapping("/deletePost")
    public ResponseEntity removePost(@RequestParam(value="postId") String postIdToBeDeleted, HttpServletRequest httpRequest) {

        String jwt = httpRequest.getHeader("jwt");
        if (!userComponent.isUserValid(jwtService.decodeJWT(jwt),
                userRepository.retrieveUserByToken(jwt))) {
            return new ResponseEntity("Post cannot be removed!", HttpStatusCode.valueOf(500));
        }

        UsersEntity user = userRepository.retrieveUserByToken(jwt);
        String postOwnerId = postTitleRepository.retrievePostTitleUserById(postIdToBeDeleted);
        if (!Objects.equals(user.getId(), postOwnerId)) {
            return new ResponseEntity("You cannot remove a post which is not yours!", HttpStatusCode.valueOf(500));
        }

        postTitleRepository.removePostById(postIdToBeDeleted);

        return new ResponseEntity("Post removed!", HttpStatusCode.valueOf(200));

    }

    private boolean isPostValid(PostTitlesEntity postTitle, PostContentsEntity postContent) {
        return postTitle.getTopic() != null && postContent.getContent() != null;
    }
}
