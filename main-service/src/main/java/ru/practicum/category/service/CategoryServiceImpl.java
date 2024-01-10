package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.CategoryMapper;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return categoryRepository.findAllOrderByName(PageRequest.of(from / size, size)).getContent();
    }

    @Override
    public CategoryDto getCategoryById(long catId) {
        return categoryRepository.findById(catId).orElseThrow(); // переписать
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return categoryRepository.save(CategoryMapper.mapToDto(newCategoryDto));
    }

    @Override
    public void deleteCategory(long catId) {
        if (categoryRepository.existsById(catId)) {
            categoryRepository.deleteById(catId);
        }
        //throw

    }

    @Override
    public CategoryDto updateCategory(long catId, CategoryDto categoryDto) {
        return null;
    }
}
