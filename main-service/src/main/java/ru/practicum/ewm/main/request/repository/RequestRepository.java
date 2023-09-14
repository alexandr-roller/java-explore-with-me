package ru.practicum.ewm.main.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.request.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRequesterId(Long requesterId);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long requesterId);

    List<Request> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<Request> findByEventId(Long eventId);

    List<Request> findByRequesterIdAndEventIdAndIdIn(Long requesterId, Long eventId, List<Long> requestIds);

    List<Request> findByIdIn(List<Long> requestIds);

    long countByEventIdAndStatus(Long eventId, Request.Status status);
}
