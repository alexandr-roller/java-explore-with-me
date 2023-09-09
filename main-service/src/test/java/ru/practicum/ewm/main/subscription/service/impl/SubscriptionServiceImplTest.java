package ru.practicum.ewm.main.subscription.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;
import ru.practicum.ewm.main.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    void shouldSubscribe() {
        Long subsId = 1L;
        Long userId = 2L;
        when(subscriptionRepository.existsBySubsIdAndUserId(subsId, userId)).thenReturn(false);
        when(userRepository.existsById(subsId)).thenReturn(true);
        when(userRepository.existsById(userId)).thenReturn(true);
        subscriptionService.subscribe(subsId, userId);

        verify(subscriptionRepository, Mockito.times(1)).save(any());
    }

    @Test
    void shouldUnsubscribe() {
        Long subsId = 1L;
        Long userId = 2L;
        when(subscriptionRepository.existsBySubsIdAndUserId(subsId, userId)).thenReturn(true);
        subscriptionService.unsubscribe(subsId, userId);

        verify(subscriptionRepository, Mockito.times(1)).deleteBySubsIdAndUserId(subsId, userId);
    }

    @Test
    void shouldFindSubscribersByUserId() {
        Long userId = 2L;
        List<User> users = List.of(User.builder().id(1L).name("Name").email("test@test.ru").build());
        when(subscriptionRepository.findSubscribersByUserId(userId, EwmPageRequest.of(0, 10))).thenReturn(users);

        assertEquals(users, subscriptionService.findSubscribersByUserId(userId, 0, 10));
    }
}