package com.example.blog.SpringBootBlogAPI.controller;

import com.example.blog.SpringBootBlogAPI.payload.CommentDto;
import com.example.blog.SpringBootBlogAPI.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentDto comment) {
        return commentService.updateComment(postId, commentId, comment);
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Successfully removed comment with id " + commentId + " on post with id " + postId);
    }

}
