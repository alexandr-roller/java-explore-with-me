package ru.practicum.ewm.main.user.service;

import ru.practicum.ewm.main.user.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);

    void deleteById(Long userId);

    List<User> findByIds(List<Long> ids, Integer from, Integer size);
}
