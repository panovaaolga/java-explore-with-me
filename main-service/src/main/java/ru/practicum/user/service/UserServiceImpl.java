package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        if (ids == null) {
            return UserMapper.mapToDtoList(userRepository
                    .findAll(PageRequest.of(from / size, size)).getContent());
        } else {
            return UserMapper.mapToDtoList(userRepository
                    .findByIdIn(ids, PageRequest.of(from / size, size)).getContent());
        }
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        return UserMapper.mapToUserDto(userRepository.save(UserMapper.mapToUser(newUserRequest)));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(User.class.getName(), userId));
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
