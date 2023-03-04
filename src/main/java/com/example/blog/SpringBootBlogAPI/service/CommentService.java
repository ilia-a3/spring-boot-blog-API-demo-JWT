package com.example.blog.SpringBootBlogAPI.service;

import com.example.blog.SpringBootBlogAPI.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);

    void deleteComment(long postId, long commentId);
    //    CommentDto getCommentById(long id);
    //    CommentDto updateComment(long postId, long id, CommentDto newComment);
    //    void deleteCommentById(long postId, long commentId);
}
