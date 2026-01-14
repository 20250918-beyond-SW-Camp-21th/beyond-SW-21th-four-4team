package com.mac4.yeopabackend.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.mac4.yeopabackend.post.PostRequest;

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
    private String image;

    @Column
    private String singleText;

    public static Post from(PostRequest req) {
        return new Post(null, req.getUserId(), req.getTitle(), req.getLocation(), req.getText(), req.getImage(), req.getSingleText());
    }

}
