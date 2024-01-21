package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.category.Category;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(int from, int size) {
        return CategoryMapper.mapToDtoList(categoryRepository
                .findAll(PageRequest.of(from / size, size)).getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryDtoById(long catId) {
        return CategoryMapper.mapToCategoryDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(Category.class.getName(), catId)));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return CategoryMapper.mapToCategoryDto(categoryRepository.save(CategoryMapper.mapToCategory(newCategoryDto)));
    }

    @Override
    public void deleteCategory(long catId) {
        categoryRepository.deleteById(catId);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() ->  new NotFoundException(Category.class.getName(), catId));
        category.setName(categoryDto.getName());
        return CategoryMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(Category.class.getName(), catId));
    }
}
