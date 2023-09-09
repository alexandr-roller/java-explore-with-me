package ru.practicum.ewm.main.subscription.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;
import ru.practicum.ewm.main.subscription.entity.Subscription;
import ru.practicum.ewm.main.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.main.subscription.service.SubscriptionService;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.exception.UserNotFoundException;
import ru.practicum.ewm.main.user.repository.UserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public List<User> subscribe(Long subsId, Long userId) {
        if (subscriptionRepository.existsBySubsIdAndUserId(subsId, userId)) {
            throw new DataIntegrityViolationException("The subscription is repeated");
        }

        if (!userRepository.existsById(subsId)) {
            throw new UserNotFoundException(subsId);
        }

        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        Subscription subscription = Subscription.builder().subsId(subsId).userId(userId).build();
        subscriptionRepository.save(subscription);

        return subscriptionRepository.findSubscriptionsBySubsId(subsId);
    }

    @Override
    public List<User> unsubscribe(Long subsId, Long userId) {
        if (!subscriptionRepository.existsBySubsIdAndUserId(subsId, userId)) {
            throw new DataIntegrityViolationException("The subscription is not exists");
        }

        subscriptionRepository.deleteBySubsIdAndUserId(subsId, userId);

        return subscriptionRepository.findSubscriptionsBySubsId(subsId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findSubscribersByUserId(Long userId, Integer from, Integer size) {
        return subscriptionRepository.findSubscribersByUserId(userId, EwmPageRequest.of(from, size));
    }

    @Override
    public List<User> findSubscriptionsBySubsId(Long subsId, Integer from, Integer size) {
        return subscriptionRepository.findSubscriptionsBySubsId(subsId, EwmPageRequest.of(from, size));
    }
}
