package com.rse.mobile.webservice.payload.requests;

import java.util.List;

public class PostRequest {
    private String caption;
    private List<String> images; // List of image URLs or file paths
    private Long userId; // ID of the user creating the post
}
