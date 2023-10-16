package com.rse.mobile.webservice.services;

import com.rse.mobile.webservice.dto.PostRequestDTO;
import com.rse.mobile.webservice.entities.Post;

public interface PostService {
    Post createPost(PostRequestDTO requestDTO);
}
