package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllOrderById(Pageable pageable);

    Page<User> findByIdInOrderById(List<Long> ids, Pageable pageable);
}
