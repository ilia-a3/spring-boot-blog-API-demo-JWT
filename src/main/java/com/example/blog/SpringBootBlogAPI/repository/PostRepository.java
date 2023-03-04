package com.example.blog.SpringBootBlogAPI.repository;

import com.example.blog.SpringBootBlogAPI.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //  JpaRepository already has all code needed
}
