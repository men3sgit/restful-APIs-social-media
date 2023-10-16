package com.rse.mobile.webservice.dto.mapper;

import com.rse.mobile.webservice.dto.PostRequestDTO;
import com.rse.mobile.webservice.payload.requests.PostRequest;

import java.util.function.Function;

public interface PostRequestDTOMapper extends Function<PostRequestDTO, PostRequest> {
}
