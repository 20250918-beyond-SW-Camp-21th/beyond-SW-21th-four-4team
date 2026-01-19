package com.mac4.yeopabackend.post.service;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.repository.PostRepository;
import com.mac4.yeopabackend.post.dto.PostRequest;
import com.mac4.yeopabackend.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void create(PostRequest postRequest, String objectKey, String originalName){

        postRepository.save(Post.form(postRequest,objectKey,originalName));
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post Not Found"));

        return new PostResponse(post.getUserId(),post.getTitle(),post.getLocation()
        ,post.getText(),post.getObjectKey(),post.getImage(),post.getSingleText());
    }

    public List<PostResponse> getAllPost(){
        return  postRepository.findAll().stream().map(post -> new PostResponse(post.getUserId(),post.getTitle(),post.getLocation()
                        ,post.getText(),post.getObjectKey(),post.getImage(),post.getSingleText()))
                .toList();

    }

}
