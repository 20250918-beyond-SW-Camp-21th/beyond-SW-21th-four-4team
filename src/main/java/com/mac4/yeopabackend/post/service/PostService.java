package com.mac4.yeopabackend.post.service;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.repository.PostRepository;
import com.mac4.yeopabackend.post.dto.PostRequest;
import com.mac4.yeopabackend.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Post getAllPost(){
        return (Post) postRepository.findAll();

    }

}
