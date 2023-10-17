package com.rse.mobile.webservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO(
        Long id,
        String caption,
        List<String> images,
        Integer likes,
        Integer comments,
        Integer shares,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
