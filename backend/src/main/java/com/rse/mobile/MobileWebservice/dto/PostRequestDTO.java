package com.rse.mobile.MobileWebservice.dto;

import lombok.Getter;

import java.util.List;

public record PostRequestDTO(String caption,
                             List<String> images) {
}
