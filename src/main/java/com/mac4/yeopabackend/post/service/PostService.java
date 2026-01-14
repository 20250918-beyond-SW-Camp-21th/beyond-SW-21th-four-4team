package com.mac4.yeopabackend.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void create(PostRequest postRequest){
        postRepository.save(Post.from(postRequest));
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        return new PostResponse(post.getUserId(),post.getTitle(),post.getLocation()
        ,post.getText(),post.getImage(),post.getSingleText());
    }

    public PostResponse getAllPost(){
        
    }

}
