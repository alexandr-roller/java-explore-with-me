package ru.practicum.ewm.main.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.repository.UserRepository;
import ru.practicum.ewm.main.user.service.UserService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByIds(List<Long> ids, Integer from, Integer size) {
        return userRepository.findByIds(ids, EwmPageRequest.of(from, size));
    }
}
