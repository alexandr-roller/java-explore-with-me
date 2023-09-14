package ru.practicum.ewm.main.subscription.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.subscription.service.SubscriptionService;
import ru.practicum.ewm.main.user.dto.UserDto;
import ru.practicum.ewm.main.user.mapper.UserMapper;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(value = "/users/{userId}")
@RequiredArgsConstructor
@Validated
@Slf4j
public class SubscriptionController {
    public final SubscriptionService subscriptionService;
    public final UserMapper userMapper;

    @PostMapping(value = "/subscribers/{sourceUserId}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDto> subscribe(
            @PathVariable Long userId,
            @PathVariable Long sourceUserId
    ) {
        log.info("Post subscription, subsId={}, userId={}", userId, sourceUserId);
        return userMapper.toDTOs(subscriptionService.subscribe(userId, sourceUserId));
    }

    @DeleteMapping(value = "/subscribers/{sourceUserId}")
    public List<UserDto> unsubscribe(
            @PathVariable Long userId,
            @PathVariable Long sourceUserId
    ) {
        log.info("Delete subscription, subsId={}, userId={}", userId, sourceUserId);
        return userMapper.toDTOs(subscriptionService.unsubscribe(userId, sourceUserId));
    }

    @GetMapping(value = "/subscribers")
    public List<UserDto> getSubscribers(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get subscribers, userId={}", userId);
        return userMapper.toDTOs(subscriptionService.findSubscribersByUserId(userId, from, size));
    }

    @GetMapping(value = "/subscriptions")
    public List<UserDto> getSubscriptions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get subscriptions, userId={}", userId);
        return userMapper.toDTOs(subscriptionService.findSubscriptionsBySubsId(userId, from, size));
    }
}
