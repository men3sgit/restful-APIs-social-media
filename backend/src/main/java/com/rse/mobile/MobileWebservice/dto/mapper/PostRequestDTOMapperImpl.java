package com.rse.mobile.MobileWebservice.dto.mapper;

import com.rse.mobile.MobileWebservice.dto.PostRequestDTO;
import com.rse.mobile.MobileWebservice.model.requests.PostRequest;

public class PostRequestDTOMapperImpl implements PostRequestDTOMapper {

    @Override
    public PostRequest apply(PostRequestDTO postRequestDTO) {
        return new PostRequest(

        );
    }
}
