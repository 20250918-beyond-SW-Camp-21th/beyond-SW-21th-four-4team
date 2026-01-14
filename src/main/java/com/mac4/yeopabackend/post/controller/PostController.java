package com.mac4.yeopabackend.post.controller;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.dto.PostRequest;
import com.mac4.yeopabackend.post.dto.PostResponse;
import com.mac4.yeopabackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public void creatPost (@RequestBody PostRequest postRequest) {

        postService.create(postRequest);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping
    public Post getAllPost(){
        return postService.getAllPost();
    }

}
