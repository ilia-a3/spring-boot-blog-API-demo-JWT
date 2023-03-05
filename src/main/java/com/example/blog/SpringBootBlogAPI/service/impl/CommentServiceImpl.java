package com.example.blog.SpringBootBlogAPI.service.impl;

import com.example.blog.SpringBootBlogAPI.entity.Comment;
import com.example.blog.SpringBootBlogAPI.entity.Post;
import com.example.blog.SpringBootBlogAPI.exception.BlogAPIException;
import com.example.blog.SpringBootBlogAPI.exception.ResourceNotFoundException;
import com.example.blog.SpringBootBlogAPI.payload.CommentDto;
import com.example.blog.SpringBootBlogAPI.repository.CommentRepository;
import com.example.blog.SpringBootBlogAPI.repository.PostRepository;
import com.example.blog.SpringBootBlogAPI.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToComment(commentDto);

        //  retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        //  save comment entity to database

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //  retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert to list of CommentDto
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        return mapToDto(getCommentAndValidatePost(postId, commentId));
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentReq) {
        Comment comment = getCommentAndValidatePost(postId, commentId);

        if (commentReq.getName() != null) comment.setName(commentReq.getName());
        if (commentReq.getEmail() != null) comment.setEmail(commentReq.getEmail());
        if (commentReq.getBody() != null) comment.setBody(commentReq.getBody());

        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = getCommentAndValidatePost(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentAndValidatePost(long postId, long commentId) {
        //  retrieve post by id
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        //  retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));

        if (!(comment.getPost().getId() == post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return comment;
    }

    //    @Override
    //    public CommentDto getCommentById(long id) {
    //        Comment comment = getCommentEntityById(id);
    //        return mapToDto(comment);
    //    }
    //
    //    @Override
    //    public CommentDto updateComment(long postId, long commentId, CommentDto comment) {
    //        Comment newComment = getCommentEntityById(postId, commentId);
    //        if (comment.getName() != null) {
    //            newComment.setName(comment.getName());
    //        }
    //        if (comment.getEmail() != null) {
    //            newComment.setEmail(comment.getEmail());
    //        }
    //        if (comment.getBody() != null) {
    //            newComment.setBody(comment.getBody());
    //        }
    //
    //        return mapToDto(commentRepository.save(newComment));
    //    }
    //
    //    @Override
    //    public void deleteCommentById(long postId, long commentId) {
    //        Comment comment = getCommentEntityById(commentId);
    //        commentRepository.delete(comment);
    //    }
    //
    //    private Comment getCommentEntityById(long commentId) {
    //        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", "" + commentId));
    //    }
    //    private Post getPostAndCommentEntityById(long postId, long commentId) {
    //        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", "" + postId));
    //        Comment comment = getCommentEntityById(commentId);
    //
    //        if (!(comment.getPost().getId() == post.getId())) {
    //        }
    //    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        //        CommentDto commentDto = new CommentDto();
        //        commentDto.setId(comment.getId());
        //        commentDto.setName(comment.getName());
        //        commentDto.setEmail(comment.getEmail());
        //        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    private Comment mapToComment(CommentDto commentDto) {
//        return new Comment(commentDto.getId(), commentDto.getName(), commentDto.getEmail(), commentDto.getBody());

        return mapper.map(commentDto, Comment.class);
    }
}
