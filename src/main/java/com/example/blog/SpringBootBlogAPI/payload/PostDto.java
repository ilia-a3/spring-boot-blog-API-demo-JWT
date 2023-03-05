package com.example.blog.SpringBootBlogAPI.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    //  title should not be empty or null
    //  title should be at least 2 characters long
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    //  desc should not be empty or null
    //  desc should be at least 10 characters long
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    //  should not be null or empty
    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
    private long categoryId;
}
