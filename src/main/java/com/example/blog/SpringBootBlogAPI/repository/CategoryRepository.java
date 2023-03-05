package com.example.blog.SpringBootBlogAPI.repository;

import com.example.blog.SpringBootBlogAPI.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}