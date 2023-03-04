package com.example.blog.SpringBootBlogAPI.service.impl;

import com.example.blog.SpringBootBlogAPI.entity.Post;
import com.example.blog.SpringBootBlogAPI.exception.ResourceNotFoundException;
import com.example.blog.SpringBootBlogAPI.payload.PostDto;
import com.example.blog.SpringBootBlogAPI.payload.PostResponse;
import com.example.blog.SpringBootBlogAPI.repository.PostRepository;
import com.example.blog.SpringBootBlogAPI.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //  convert Dto to entity
        Post post = mapToEntity(postDto);
        //        Post post = new Post();
        //        post.setTitle(postDto.getTitle());
        //        post.setDescription(postDto.getDescription());
        //        post.setContent(postDto.getContent());

        Post newPost = postRepository.save(post);
        //  convert entity back into PostDto

        PostDto postResponse = mapToDto(newPost);
        //        PostDto postResponse = new PostDto();
        //        postResponse.setId(newPost.getId());
        //        postResponse.setTitle(newPost.getTitle());
        //        postResponse.setDescription(newPost.getDescription());
        //        postResponse.setContent(newPost.getContent());

        return postResponse;
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //  pagination
        //        Pageable pageable = PageRequest.of(pageNo, pageSize);
        //
        //  pagination with sorting
        Sort sorter = Sort.by(sortBy);
        if (sortDir.equalsIgnoreCase("desc")){
            sorter = sorter.descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sorter);
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        Page<Post> posts = postRepository.findAll(pageable);

        // converting Page to List
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(this::mapToDto).toList();
        PostResponse response = new PostResponse(content, pageNo, pageSize, posts.getTotalElements(), posts.getTotalPages(),posts.isLast());

        return response;
        //        return listOfPosts.stream().map(this::postToDTO).collect(Collectors.toList());

        //  before pagination
        //        List<Post> posts = postRepository.findAll();
        //        return posts.stream().map(this::postToDTO).collect(Collectors.toList());
        ////

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        //  get post by id on database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        if (postDto.getTitle() != null) {
            post.setTitle(postDto.getTitle());
        }
        if (postDto.getDescription() != null) {
            post.setDescription(postDto.getDescription());
        }
        if (postDto.getContent() != null) {
            post.setContent(postDto.getContent());
        }


        Post updatePost = postRepository.save(post);

        return mapToDto(updatePost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(id)));
        postRepository.delete(post);


    }
    //    @Override
    //    public String deletePost(long id) {
    //        if (postRepository.existsById(id)){
    //            postRepository.deleteById(id);
    //            return "Successfully deleted post with id = " + id;
    //        }
    //
    //        return "Couldn't find post with id = " + id;
    //    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);

        //        PostDto postDto = new PostDto();
        //        postDto.setId(post.getId());
        //        postDto.setTitle(post.getTitle());
        //        postDto.setDescription(post.getDescription());
        //        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);

        //        Post post = new Post();
        //        post.setTitle(postDto.getTitle());
        //        post.setDescription(postDto.getDescription());
        //        post.setContent(postDto.getContent());

        return post;
    }
}







