package com.example.blog.SpringBootBlogAPI.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    //  should not be null or empty
    @NotEmpty(message = "name should not be null or empty")
    private String name;

    //  should not be null or empty
    //  email field validation
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    //  should not be null or empty
    //  should be minimum 10 characters
    @Size(min = 10, message = "body must be at least 10 characters")
    private String body;
}
