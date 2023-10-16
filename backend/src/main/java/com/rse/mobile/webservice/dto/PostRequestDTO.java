package com.rse.mobile.webservice.dto;

import java.util.List;

public record PostRequestDTO(String caption,
                             List<String> images) {
}
