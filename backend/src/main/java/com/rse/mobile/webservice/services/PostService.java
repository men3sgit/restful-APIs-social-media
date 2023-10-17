package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.dto.PostDTO;
import com.rse.mobile.webservice.entities.Post;
import com.rse.mobile.webservice.payload.requests.PostRequest;

import java.util.List;

public interface PostService {
    Post createPost(PostRequest request);
    List<PostDTO> getAllPostsById(Long userId);
}
