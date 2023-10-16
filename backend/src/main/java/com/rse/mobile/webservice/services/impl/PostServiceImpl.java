package com.rse.mobile.webservice.services.impl;

import com.rse.mobile.webservice.dto.PostRequestDTO;
import com.rse.mobile.webservice.exceptions.request.ApiRequestException;
import com.rse.mobile.webservice.entities.Post;
import com.rse.mobile.webservice.entities.User;
import com.rse.mobile.webservice.repositories.PostRepository;
import com.rse.mobile.webservice.services.AuthenticationService;
import com.rse.mobile.webservice.services.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    private final AuthenticationService authenticationService;
    private final PostRepository postRepository;

    @Override
    public Post createPost(PostRequestDTO postRequest) {
        User currentUser = authenticationService.getCurrentAuthenticatedUser();
        if (currentUser == null) {
            LOGGER.error("User is not authenticated.");
            throw new ApiRequestException("User is not authenticated.");
        }

        LOGGER.info("Creating a new post for user with ID: {}", currentUser.getId());

        Post post = new Post();
        post.setUser(currentUser);
        post.setCaption(postRequest.caption());
        post.setImages(postRequest.images()); // Assuming you have a list of image URLs in the request.

        Post createdPost = postRepository.save(post);

        LOGGER.info("Post created successfully with ID: {}", createdPost.getId());
        return createdPost;
    }
}
