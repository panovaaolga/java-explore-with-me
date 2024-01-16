package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.NotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.UserMapper;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        if (ids == null) {
            return UserMapper.mapToDtoList(userRepository
                    .findAllOrderById(PageRequest.of(from / size, size)).getContent());
        } else {
            return UserMapper.mapToDtoList(userRepository
                    .findByIdInOrderById(ids, PageRequest.of(from / size, size)).getContent());
        }
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        return UserMapper.mapToUserDto(userRepository.save(UserMapper.mapToUser(newUserRequest)));
    }

    @Override
    public void deleteUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
        throw new NotFoundException(User.class.getName(), userId);
    }
}
