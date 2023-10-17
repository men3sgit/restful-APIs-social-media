package com.rse.mobile.webservice.dto.mapper;

import com.rse.mobile.webservice.dto.PostDTO;
import com.rse.mobile.webservice.entities.Post;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDTOMapper implements DTOMapper<Post, PostDTO> {
    @Override
    public PostDTO apply(Post post) {
        return new PostDTO(
                post.getId(),
                post.getCaption(),
                post.getImages(),
                post.getLikes().size(),
                post.getComments().size(),
                post.getSaves().size(),
                post.getUser().getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
