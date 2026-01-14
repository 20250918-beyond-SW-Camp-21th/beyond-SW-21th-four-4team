package com.mac4.yeopabackend.post;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

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
