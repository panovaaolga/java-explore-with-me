package ru.practicum.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.Compilation;

public interface CompilationRepo extends JpaRepository<Compilation, Long> {

    Page<Compilation> findAllOrderById(Pageable pageable);

    Page<Compilation> findByPinnedOrderById(boolean pinned, Pageable pageable);
}
