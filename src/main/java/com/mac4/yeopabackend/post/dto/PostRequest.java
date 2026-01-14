package com.mac4.yeopabackend.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostRequest {
    private Long userId;
    private String location;
    private String title;
    private String text;
    private String singleText;
    private String image;
}
