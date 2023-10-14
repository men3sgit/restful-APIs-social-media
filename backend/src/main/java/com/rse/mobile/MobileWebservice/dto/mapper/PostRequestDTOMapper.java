package com.rse.mobile.MobileWebservice.dto.mapper;

import com.rse.mobile.MobileWebservice.dto.PostRequestDTO;
import com.rse.mobile.MobileWebservice.model.requests.PostRequest;

import java.util.function.Function;

public interface PostRequestDTOMapper extends Function<PostRequestDTO, PostRequest> {
}
