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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return CategoryMapper.mapToDtoList(categoryRepository
                .findAll(PageRequest.of(from / size, size)).getContent());
    }

    @Override
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
        if (categoryRepository.existsById(catId)) {
            categoryRepository.deleteById(catId);
        }
        throw new NotFoundException(Category.class.getName(), catId);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() ->  new NotFoundException(Category.class.getName(), catId));
        if (!categoryDto.getName().equals(category.getName())) {
            category.setName(categoryDto.getName());
        }
        return CategoryMapper.mapToCategoryDto(categoryRepository.save(category));
    }

    public Category getCategoryById(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(Category.class.getName(), catId));
    }
}
