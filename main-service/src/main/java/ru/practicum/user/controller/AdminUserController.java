package ru.practicum.user.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    @GetMapping
    public List<UserDto> getUsers(@RequestParam List<Integer> ids,
                                  @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                  @Positive @RequestParam(defaultValue = "10") int size) {
        return null;
    }

    @PostMapping
    public UserDto createUser(@RequestBody NewUserRequest newUserRequest) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {

    }
}
