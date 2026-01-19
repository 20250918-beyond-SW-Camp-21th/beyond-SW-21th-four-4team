package com.mac4.yeopabackend.post.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mac4.yeopabackend.post.dto.PostRequest;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private Long userId;

    @Column
    private String title;

    @Column
    private String location;

    @Column
    private String text;

    @Column
    private String objectKey;

    @Column
    private String originalName;

    @Column
    private String image;

    @Column
    private String singleText;

    public static Post form(PostRequest req, String image, String objectKey, String originalName) {
        return new Post(null, req.getUserId(), req.getTitle(), req.getLocation(), req.getText(),objectKey, originalName, image, req.getSingleText());
    }

}
