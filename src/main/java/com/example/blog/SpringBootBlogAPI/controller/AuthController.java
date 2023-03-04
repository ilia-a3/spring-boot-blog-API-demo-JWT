package com.example.blog.SpringBootBlogAPI.controller;

import com.example.blog.SpringBootBlogAPI.payload.JwtAuthResponse;
import com.example.blog.SpringBootBlogAPI.payload.LoginDto;
import com.example.blog.SpringBootBlogAPI.payload.RegisterDto;
import com.example.blog.SpringBootBlogAPI.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //  build login REST API
    @PostMapping(value = {"login", "signin", "sign-in"})
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    //  build register REST API
    @PostMapping({"register", "signup", "sign-up"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
//        System.out.println(registerDto.getUsername());
//        System.out.println(registerDto.getName());
//        System.out.println(registerDto.getEmail());
//        System.out.println(registerDto.getPassword());
        String res = authService.register(registerDto);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
