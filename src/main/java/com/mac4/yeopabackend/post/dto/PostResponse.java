package com.mac4.yeopabackend.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostResponse {
    Long userId;
    String title;
    String location;
    String text;
    String objectKey;
    String image;
    String singleText;
}
