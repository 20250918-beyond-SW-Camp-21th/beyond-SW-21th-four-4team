package com.mac4.yeopabackend.post.controller;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.dto.PostRequest;
import com.mac4.yeopabackend.post.dto.PostResponse;
import com.mac4.yeopabackend.post.service.FileService;
import com.mac4.yeopabackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import com.mac4.yeopabackend.post.dto.FileInfo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> creatPost (
            @ModelAttribute PostRequest request) throws IOException {
        MultipartFile file = request.getFile();
        FileInfo fileName = fileService.uploadFile(file);
        postService.create(request, fileName.objectKey(),fileName.originalName());

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> list() {
        return ResponseEntity.ok(fileService.listFiles());
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping
    public List<PostResponse> getAllPost(){
        return postService.getAllPost();
    }



}
