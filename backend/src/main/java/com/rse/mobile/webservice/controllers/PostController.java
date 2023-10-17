package com.rse.mobile.webservice.controllers;

import com.rse.mobile.webservice.dto.PostDTO;
import com.rse.mobile.webservice.entities.Post;
import com.rse.mobile.webservice.helper.MessageConstant;
import com.rse.mobile.webservice.payload.reponses.ResponseHandler;
import com.rse.mobile.webservice.payload.requests.PostRequest;
import com.rse.mobile.webservice.services.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final ResponseHandler responseHandler;

    @PostMapping(path = "create")
    public ResponseEntity<?> create(@RequestBody PostRequest request) {
        postService.createPost(request);
        return responseHandler.buildSuccessResponse(MessageConstant.MSG_CREATE_POST_SUCCESSFUL, Map.of("data", request));
    }
    @GetMapping(path = "{userId}")
    public ResponseEntity<?> getAll(@PathVariable(value = "userId") Long userId){
        List<PostDTO> posts = postService.getAllPostsById(userId);

        return responseHandler.buildSuccessResponse(MessageConstant.MSG_VIEW_POST_SUCCESSFUL, Map.of("data", posts));
    }

}