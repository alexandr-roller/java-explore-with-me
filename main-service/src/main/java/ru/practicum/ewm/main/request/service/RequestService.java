package ru.practicum.ewm.main.request.service;

import ru.practicum.ewm.main.request.entity.Request;

import java.util.List;

public interface RequestService {
    Request create(Long requesterId, Long eventId);

    Request findById(Long requestId);

    List<Request> findByRequesterId(Long requesterId);

    List<Request> findByUserIdAndEventId(Long userId, Long eventId);

    Request cancel(Long requesterId, Long requestId);

    List<Request> updateStatus(Long requesterId, Long eventId, List<Long> requestIds, Request.Status status);
}
