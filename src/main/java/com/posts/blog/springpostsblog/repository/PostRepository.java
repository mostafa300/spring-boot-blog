package com.posts.blog.springpostsblog.repository;

import com.posts.blog.springpostsblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
