package ru.practicum.ewm.main.event.service;

import ru.practicum.ewm.main.event.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event create(Event event, Long userId, Long catId);

    Event updateByUser(Event event, Long userId);

    Event updateByAdmin(Event event);

    List<Event> findByInitiatorId(Long userId, Integer from, Integer size);

    Event findByIdAndInitiatorId(Long eventId, Long userId);

    Event findById(Long eventId);

    Event findByIdPublished(Long eventId);

    List<Event> findByPublicParams(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            SortType sort,
            Integer from,
            Integer size
    );

    List<Event> findByAdminParams(
            List<Long> userIds,
            List<Event.State> states,
            List<Long> categoryIds,
            LocalDateTime start,
            LocalDateTime end,
            Integer from,
            Integer size
    );

    List<Event> findBySubscriberId(Long subsId, Integer from, Integer size);

    enum SortType {
        EVENT_DATE, VIEWS
    }
}
