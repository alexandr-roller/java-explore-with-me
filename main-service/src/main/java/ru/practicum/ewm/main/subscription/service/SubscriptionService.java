package ru.practicum.ewm.main.subscription.service;

import ru.practicum.ewm.main.user.entity.User;

import java.util.List;

public interface SubscriptionService {
    List<User> subscribe(Long subsId, Long userId);

    List<User> unsubscribe(Long subsId, Long userId);

    List<User> findSubscribersByUserId(Long userId, Integer from, Integer size);

    List<User> findSubscriptionsBySubsId(Long userId, Integer from, Integer size);
}
