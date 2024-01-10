package ru.practicum.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.dto.CompilationDto;

public interface CompilationRepo extends JpaRepository<CompilationDto, Long> {

    Page<CompilationDto> findAllOrderByTitle(Pageable pageable);

    Page<CompilationDto> findByPinnedOrderByTitle(boolean pinned, Pageable pageable);
}
