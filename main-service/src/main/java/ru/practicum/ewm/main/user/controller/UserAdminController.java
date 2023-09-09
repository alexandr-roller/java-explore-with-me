package ru.practicum.ewm.main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.user.dto.UserDto;
import ru.practicum.ewm.main.user.mapper.UserMapper;
import ru.practicum.ewm.main.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(value = "/admin/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserAdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> get(
            @RequestParam @Nullable List<Long> ids,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get users ids={}, from={}, size={}", ids, from, size);
        return userMapper.toDTOs(userService.findByIds(ids, from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("Post user");
        return userMapper.toDTO(
                userService.create(
                        userMapper.fromDTO(userDto)
                )
        );
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        log.info("Delete user id={}", userId);
        userService.deleteById(userId);
    }
 }
