package com.example.blog.SpringBootBlogAPI.service;

import com.example.blog.SpringBootBlogAPI.payload.PostDto;
import com.example.blog.SpringBootBlogAPI.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
//    List<PostDto> getAllPosts(int pageNo, int pageSize);

    PostDto getPostById(long id);

    PostDto updatePostById(PostDto postDto, long id);

    void deletePost(long id);
    //    String deletePost(long id);

    List<PostDto> getPostsByCategory(long categoryId);
}
