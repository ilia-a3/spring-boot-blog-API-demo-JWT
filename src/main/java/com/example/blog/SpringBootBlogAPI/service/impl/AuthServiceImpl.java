package com.example.blog.SpringBootBlogAPI.service.impl;

import com.example.blog.SpringBootBlogAPI.entity.Role;
import com.example.blog.SpringBootBlogAPI.entity.User;
import com.example.blog.SpringBootBlogAPI.exception.BlogAPIException;
import com.example.blog.SpringBootBlogAPI.payload.LoginDto;
import com.example.blog.SpringBootBlogAPI.payload.RegisterDto;
import com.example.blog.SpringBootBlogAPI.repository.RoleRepository;
import com.example.blog.SpringBootBlogAPI.repository.UserRepository;
import com.example.blog.SpringBootBlogAPI.security.JwtTokenProvider;
import com.example.blog.SpringBootBlogAPI.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication auth =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtTokenProvider.generateToken(auth);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        //  add check for username exists in db
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        //  add check for email exists in db
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");

        }

        User user = new User(registerDto.getName(), registerDto.getUsername(), registerDto.getEmail(), passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);
        return "User registered successfully";
    }
}
