package ru.practicum.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.compilation.Compilation;

@Repository
public interface CompilationRepo extends JpaRepository<Compilation, Long> {

    Page<Compilation> findAll(Pageable pageable);

    Page<Compilation> findByPinned(boolean pinned, Pageable pageable);
}
