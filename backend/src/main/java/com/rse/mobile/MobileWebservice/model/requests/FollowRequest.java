package com.rse.mobile.MobileWebservice.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public record FollowRequest(Long followedId, Long followById) {
}
