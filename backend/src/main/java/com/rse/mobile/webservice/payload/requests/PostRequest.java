package com.rse.mobile.webservice.payload.requests;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private String caption;
    private List<String> images; // List of image URLs or file paths

    public PostRequest(String caption) {
        this.caption = caption;
    }
}
