package com.rse.mobile.MobileWebservice.service;

import com.rse.mobile.MobileWebservice.dto.PostRequestDTO;
import com.rse.mobile.MobileWebservice.model.entities.Post;

public interface PostService {
    Post createPost(PostRequestDTO requestDTO);
}
