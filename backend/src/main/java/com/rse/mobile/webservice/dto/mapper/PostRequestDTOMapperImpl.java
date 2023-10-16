package com.rse.mobile.webservice.dto.mapper;

import com.rse.mobile.webservice.dto.PostRequestDTO;
import com.rse.mobile.webservice.payload.requests.PostRequest;

public class PostRequestDTOMapperImpl implements PostRequestDTOMapper {

    @Override
    public PostRequest apply(PostRequestDTO postRequestDTO) {
        return new PostRequest(

        );
    }
}
