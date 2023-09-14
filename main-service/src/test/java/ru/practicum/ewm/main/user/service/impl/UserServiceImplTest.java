package ru.practicum.ewm.main.user.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private final User user = User.builder().id(1L).name("Name").email("test@test.ru").build();

    @Test
    void shouldCreate() {
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(user, userService.create(user));
    }

    @Test
    void shouldDeleteById() {
        long id = 1L;
        userService.deleteById(id);

        verify(userRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void shouldFindByIds() {
        List<Long> ids = List.of(1L);
        List<User> users = List.of(user);
        when(userRepository.findByIds(any(), any())).thenReturn(users);

        assertEquals(users, userService.findByIds(ids, 0, 10));
    }
}