package com.fodapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fodapi.components.UserComponent;
import com.fodapi.dto.FullPostDTO;
import com.fodapi.dto.ViewablePostDTO;
import com.fodapi.entity.PostContentsEntity;
import com.fodapi.entity.PostTitlesEntity;
import com.fodapi.entity.UsersEntity;
import com.fodapi.repository.*;
import com.fodapi.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
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

    @Autowired
    FullPostRepository fullPostRepository;

    @Autowired
    ViewablePostRepository viewablePostRepository;

    @PostMapping("/addPost")
    public ResponseEntity addPost(@RequestBody Map<String, String> requestBody) {
        PostTitlesEntity postTitle = new PostTitlesEntity();
        postTitle.setTopic(requestBody.get("topic"));
        PostContentsEntity postContent = new PostContentsEntity();
        postContent.setContent(requestBody.get("content"));
        postContent.setImagePath(requestBody.getOrDefault("image_path", null));

        UsersEntity user = userRepository.retrieveUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

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
    public ResponseEntity editPost(@RequestBody Map<String, String> requestBody) {
        PostTitlesEntity postTitle = new PostTitlesEntity();
        postTitle.setTopic(requestBody.get("topic"));
        postTitle.setId(requestBody.get("postId"));
        PostContentsEntity postContent = new PostContentsEntity();
        postContent.setContent(requestBody.get("content"));
        postContent.setImagePath(requestBody.getOrDefault("image_path", null));

        if (!isPostValid(postTitle, postContent) || postTitle.getId() == null) {
            return new ResponseEntity("Post is not valid!", HttpStatusCode.valueOf(500));
        }

        UsersEntity user = userRepository.retrieveUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!Objects.equals(postTitleRepository.retrieveUserIdByPostTitleId(postTitle.getId()), user.getId())) {
            return new ResponseEntity("You can only edit your posts!", HttpStatusCode.valueOf(500));
        }

        postTitleRepository.updatePostTitle(postTitle.getTopic(), postTitle.getId());

        postContentRepository.updatePostContentById(postTitle.getId(),
                postContent.getContent(),
                postContent.getImagePath());

        return new ResponseEntity("Post edited!", HttpStatusCode.valueOf(200));

    }

    @GetMapping("/deletePost")
    public ResponseEntity removePost(@RequestParam(value="postId") String postIdToBeDeleted, HttpServletResponse httpServletResponse) {
        UsersEntity user = userRepository.retrieveUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        String postOwnerId = postTitleRepository.retrieveUserIdByPostTitleId(postIdToBeDeleted);
        if (!Objects.equals(user.getId(), postOwnerId)) {
            return new ResponseEntity("You cannot remove a post which is not yours!", HttpStatusCode.valueOf(500));
        }

        postTitleRepository.removePostById(postIdToBeDeleted);

        return new ResponseEntity("Post removed!", HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/displayPosts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayPosts(@RequestParam(value="startIndex") String startIndex,
                                       @RequestParam(value="endIndex") String endIndex) throws JsonProcessingException {

        if (Integer.parseInt(startIndex) < 0 ||
                Integer.parseInt(startIndex) >= Integer.parseInt(endIndex) ||
                Integer.parseInt(endIndex) < 0) {
            return new ResponseEntity("Wrong indexes!", HttpStatusCode.valueOf(500));
        }

        List<FullPostDTO> fullPostDTOS = fullPostRepository.getPostContent();
        if (fullPostDTOS.size() > Integer.parseInt(endIndex)) {
            fullPostDTOS = fullPostDTOS.subList(Integer.parseInt(startIndex), Integer.parseInt(endIndex));
        }

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(fullPostDTOS);

        return new ResponseEntity(json, HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/displayPost", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayPost(@RequestParam(value = "postId") String postId) throws JsonProcessingException {

        List<ViewablePostDTO> viewablePostDTOS = viewablePostRepository.retrieveViewablePostWithComments(postId);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(viewablePostDTOS);

        return new ResponseEntity(json, HttpStatusCode.valueOf(200));
    }

    private boolean isPostValid(PostTitlesEntity postTitle, PostContentsEntity postContent) {
        return postTitle.getTopic() != null && postContent.getContent() != null;
    }
}
