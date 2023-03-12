package com.example.blog.SpringBootBlogAPI.controller;

import com.example.blog.SpringBootBlogAPI.payload.PostDto;
import com.example.blog.SpringBootBlogAPI.payload.PostResponse;
import com.example.blog.SpringBootBlogAPI.service.PostService;
import com.example.blog.SpringBootBlogAPI.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/posts")
@RequestMapping("/api/")
//@RequestMapping("/api")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //  create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postData) {
        return new ResponseEntity<>(postService.createPost(postData), HttpStatus.CREATED);
    }

    @GetMapping("v1/posts")
    //    public List<PostDto> getAllPosts(
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //  using URI versioning
    @GetMapping("v1/posts/{id}")

//  using query param versioning
//    @GetMapping(value = "posts/{id}", params = "version=1")

//  using headers versioning
//    @GetMapping(value = "posts/{id}", headers = "X-API-VERSION=1")

//  using content negotiation versioning
//    @GetMapping(value = "posts/{id}", produces = "application/vnd.example.v1+json")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

//    // get post by id V2
////  using URI versioning
//    @GetMapping("v2/posts/{id}")
//
////  using query param versioning
////    @GetMapping(value = "posts/{id}", params = "version=2")
//
////  using headers versioning
////    @GetMapping(value = "posts/{id}", headers = "X-API-VERSION=2")
//
////  using content negotiation versioning
////    @GetMapping(value = "posts/{id}", produces = "application/vnd.example.v2+json")
//    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable long id) {
//        PostDto v1 = postService.getPostById(id);
//
//        PostDtoV2 v2 = new PostDtoV2();
//        v2.setId(v1.getId());
//        v2.setComments(v1.getComments());
//        v2.setTitle(v1.getTitle());
//        v2.setContent(v1.getContent());
//        v2.setDescription(v1.getDescription());
//        v2.setCategoryId(v1.getCategoryId());
//
//        ArrayList<String> tags = new ArrayList<String>();
//        tags.add("Java");
//        tags.add("Spring Boot");
//        tags.add("AWS");
//
//        v2.setTags(tags);
//
//        return ResponseEntity.ok(v2);
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("v1/posts/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable long id) {
        return ResponseEntity.ok(postService.updatePostById(postDto, id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("v1/posts/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
    }

    //  Build GET posts by category REST API
    //  http://localhost:8080/api/posts/category/1
    @GetMapping("v1/posts/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }
}
