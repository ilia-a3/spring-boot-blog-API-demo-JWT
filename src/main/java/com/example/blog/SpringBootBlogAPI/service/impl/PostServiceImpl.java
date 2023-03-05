package com.example.blog.SpringBootBlogAPI.service.impl;

import com.example.blog.SpringBootBlogAPI.entity.Category;
import com.example.blog.SpringBootBlogAPI.entity.Post;
import com.example.blog.SpringBootBlogAPI.exception.ResourceNotFoundException;
import com.example.blog.SpringBootBlogAPI.payload.PostDto;
import com.example.blog.SpringBootBlogAPI.payload.PostResponse;
import com.example.blog.SpringBootBlogAPI.repository.CategoryRepository;
import com.example.blog.SpringBootBlogAPI.repository.PostRepository;
import com.example.blog.SpringBootBlogAPI.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        //  convert Dto to entity
        Post post = mapToEntity(postDto);
        //        Post post = new Post();
        //        post.setTitle(postDto.getTitle());
        //        post.setDescription(postDto.getDescription());
        //        post.setContent(postDto.getContent());

        post.setCategory(category);

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
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        //  get post by id on database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        if (postDto.getTitle() != null) {
            post.setTitle(postDto.getTitle());
        }
        if (postDto.getDescription() != null) {
            post.setDescription(postDto.getDescription());
        }
        if (postDto.getContent() != null) {
            post.setContent(postDto.getContent());
        }
        if (postDto.getCategoryId() != 0) {
            post.setCategory(category);
        }

        Post updatePost = postRepository.save(post);

        return mapToDto(updatePost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
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


    @Override
    public List<PostDto> getPostsByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

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







