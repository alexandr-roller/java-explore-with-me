package ru.practicum.ewm.main.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.category.repository.CategoryRepository;
import ru.practicum.ewm.main.common.EwmCommonMethods;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.exception.EventNotFoundException;
import ru.practicum.ewm.main.event.repository.EventRepository;
import ru.practicum.ewm.main.event.service.EventService;
import ru.practicum.ewm.main.stats.service.StatsService;
import ru.practicum.ewm.main.subscription.entity.Subscription;
import ru.practicum.ewm.main.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final StatsService statsService;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(2999, 12, 31, 23, 59);

    @Override
    public Event create(Event event, Long userId, Long catId) {
        return eventRepository.save(event
                .withCreateDate(LocalDateTime.now())
                .withInitiator(userRepository.getReferenceById(userId))
                .withCategory(categoryRepository.getReferenceById(catId))
                .withState(Event.State.PENDING)
        );
    }

    @Override
    public Event updateByUser(Event event, Long userId) {
        Event eventForUpdate = findById(event.getId());

        if (eventForUpdate.getState() == Event.State.PUBLISHED) {
            throw new DataIntegrityViolationException("Event is already published");
        }

        Event.State newState = event.getState();
        Event.State oldState = eventForUpdate.getState();

        if (newState != null && newState == Event.State.CANCELED && oldState == Event.State.PUBLISHED) {
            throw new DataIntegrityViolationException("Published event cannot be cancelled");
        }

        BeanUtils.copyProperties(event, eventForUpdate, EwmCommonMethods.getNullPropertyNames(event));

        return eventRepository.save(eventForUpdate);
    }

    @Override
    public Event updateByAdmin(Event event) {
        Event eventForUpdate = findById(event.getId());

        Event.State newState = event.getState();
        Event.State oldState = eventForUpdate.getState();

        if (newState != null && newState == Event.State.PUBLISHED && oldState == Event.State.PUBLISHED) {
            throw new DataIntegrityViolationException("Event is already published");
        }

        if (newState != null && newState == Event.State.PUBLISHED && oldState == Event.State.CANCELED) {
            throw new DataIntegrityViolationException("Cancelled event cannot be published");
        }

        if (newState != null && newState == Event.State.CANCELED && oldState == Event.State.PUBLISHED) {
            throw new DataIntegrityViolationException("Published event cannot be cancelled");
        }

        BeanUtils.copyProperties(event, eventForUpdate, EwmCommonMethods.getNullPropertyNames(event));
        if (newState != null && newState.equals(Event.State.PUBLISHED)) {
            eventForUpdate.setPublishDate(LocalDateTime.now());
        }

        return eventRepository.save(eventForUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findByInitiatorId(Long userId, Integer from, Integer size) {
        return eventRepository.findByInitiatorIdOrderById(userId, EwmPageRequest.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public Event findByIdAndInitiatorId(Long eventId, Long userId) {
        return eventRepository.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new EventNotFoundException(eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public Event findById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
    }

    @Override
    @Transactional(readOnly = true)
    public Event findByIdPublished(Long eventId) {
        Event event = eventRepository.findByIdAndState(eventId, Event.State.PUBLISHED).orElseThrow(() -> new EventNotFoundException(eventId));
        statsService.loadViews(event);

        return event;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findByPublicParams(
            String text,
            List<Long> categoryIds,
            Boolean paid,
            LocalDateTime start,
            LocalDateTime end,
            Boolean onlyAvailable,
            SortType sort,
            Integer from,
            Integer size
    ) {
        List<Event> events = eventRepository.findByPublicParams(
                text,
                categoryIds,
                paid,
                start == null ? LocalDateTime.now() : start,
                end == null ? MAX_DATE : end,
                onlyAvailable,
                EwmPageRequest.of(from, size)
        );
        statsService.loadViews(events);

        if (sort != null) {
            if (sort.equals(SortType.VIEWS)) {
                Comparator<Event> comparatorViews = Comparator.comparing(Event::getViews);
                events.sort(comparatorViews);
            } else if (sort.equals(SortType.EVENT_DATE)) {
                Comparator<Event> comparatorEventDate = Comparator.comparing(Event::getEventDate);
                events.sort(comparatorEventDate);
            }
        }

        return events;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findByAdminParams(
            List<Long> userIds,
            List<Event.State> states,
            List<Long> categoryIds,
            LocalDateTime start,
            LocalDateTime end,
            Integer from,
            Integer size
    ) {
        return eventRepository.findByAdminParams(
                userIds,
                states,
                categoryIds,
                start == null ? LocalDateTime.now() : start,
                end == null ? MAX_DATE : end,
                EwmPageRequest.of(from, size)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> findBySubscriberId(Long subsId, Integer from, Integer size) {
        List<Long> initiatorIds = subscriptionRepository.findBySubsId(subsId)
                .stream()
                .map(Subscription::getUserId)
                .collect(Collectors.toList());
        List<Event> events = eventRepository.findByInitiatorIdInAndState(initiatorIds, Event.State.PUBLISHED, EwmPageRequest.of(from, size));
        statsService.loadViews(events);

        return events;
    }
}
