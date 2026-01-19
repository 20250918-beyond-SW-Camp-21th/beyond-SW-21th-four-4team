package com.mac4.yeopabackend.post.repository;

import com.mac4.yeopabackend.post.domain.Post;
import com.mac4.yeopabackend.post.dto.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById(Long id);

}
