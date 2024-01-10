package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDto, Long> {

    Page<UserDto> findAllOrderByName(Pageable pageable);

    Page<UserDto> findByIdIn(List<Long> ids, Pageable pageable);
}
