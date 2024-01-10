package ru.practicum.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.dto.CategoryDto;

public interface CategoryRepository extends JpaRepository<CategoryDto, Long> {

    Page<CategoryDto> findAllOrderByName(Pageable pageable);
}
