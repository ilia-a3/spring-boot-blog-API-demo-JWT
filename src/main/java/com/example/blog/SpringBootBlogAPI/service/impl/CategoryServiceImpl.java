package com.example.blog.SpringBootBlogAPI.service.impl;

import com.example.blog.SpringBootBlogAPI.entity.Category;
import com.example.blog.SpringBootBlogAPI.exception.ResourceNotFoundException;
import com.example.blog.SpringBootBlogAPI.payload.CategoryDto;
import com.example.blog.SpringBootBlogAPI.repository.CategoryRepository;
import com.example.blog.SpringBootBlogAPI.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapToDto(savedCategory);
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category =
                categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        return mapToDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        
        category.setName(categoryDto.getName() == null ? category.getName() : categoryDto.getName());
        category.setDescription(categoryDto.getDescription() == null ? category.getDescription() : categoryDto.getDescription());

        category = categoryRepository.save(category);
        return mapToDto(category);
    }

    @Override
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
//        categoryRepository.deleteById(id);
    }

    private Category mapToEntity(CategoryDto dto) {
        return mapper.map(dto, Category.class);
    }
    private CategoryDto mapToDto(Category entity) {
        return mapper.map(entity, CategoryDto.class);
    }
}
