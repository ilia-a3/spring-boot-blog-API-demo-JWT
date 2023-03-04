package com.example.blog.SpringBootBlogAPI.service;

import com.example.blog.SpringBootBlogAPI.payload.LoginDto;
import com.example.blog.SpringBootBlogAPI.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
