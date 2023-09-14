package ru.practicum.ewm.main.event.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.category.repository.CategoryRepository;
import ru.practicum.ewm.main.common.pageable.EwmPageRequest;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.repository.EventRepository;
import ru.practicum.ewm.main.stats.service.StatsService;
import ru.practicum.ewm.main.subscription.repository.SubscriptionRepository;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    private final User user = User.builder().id(2L).name("username").email("test@test.com").build();
    private final Category category = Category.builder().id(1L).name("category").build();
    private final Event event = Event
            .builder()
            .id(2L)
            .title("title")
            .annotation("annotation")
            .category(category)
            .initiator(user)
            .description("description")
            .eventDate(LocalDateTime.now().plusDays(1))
            .lat(1.1)
            .lon(2.2)
            .paid(true)
            .participantLimit(10)
            .requestModeration(true)
            .createDate(LocalDateTime.now())
            .state(Event.State.PUBLISHED)
            .build();
    private final List<Event> events = List.of(event);
    @Mock
    private StatsService statsService;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;
    private SubscriptionRepository subscriptionRepository;
    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void shouldCreate() {
        Long userId = 2L;
        Long catId = 1L;
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(categoryRepository.getReferenceById(catId)).thenReturn(category);
        when(eventRepository.save(any())).thenReturn(event);

        assertEquals(event, eventService.create(event, userId, catId));
    }

    @Test
    void findByInitiatorId() {
        Long initiatorId = 1L;
        when(eventRepository.findByInitiatorIdOrderById(initiatorId, EwmPageRequest.of(0, 10))).thenReturn(events);

        assertEquals(events, eventService.findByInitiatorId(initiatorId, 0, 10));
    }

    @Test
    void findByIdAndInitiatorId() {
        Long eventId = 2L;
        Long initiatorId = 1L;
        when(eventRepository.findByIdAndInitiatorId(eventId, initiatorId)).thenReturn(Optional.ofNullable(event));

        assertEquals(event, eventService.findByIdAndInitiatorId(eventId, initiatorId));
    }

    @Test
    void findByIdPublished() {
        Long eventId = 2L;
        when(eventRepository.findByIdAndState(eventId, Event.State.PUBLISHED)).thenReturn(Optional.ofNullable(event));

        assertEquals(event, eventService.findByIdPublished(eventId));
    }

    @Test
    void findByPublicParams() {
        LocalDateTime start = LocalDateTime.now().minusHours(1L);
        LocalDateTime end = LocalDateTime.now().plusHours(2L);
        when(eventRepository.findByPublicParams(null, null, true, start, end, true, EwmPageRequest.of(0, 10))).thenReturn(events);

        assertEquals(events, eventService.findByPublicParams(null, null, true, start, end, true, null, 0, 10));
    }

    @Test
    void findByAdminParams() {
        LocalDateTime start = LocalDateTime.now().minusHours(1L);
        LocalDateTime end = LocalDateTime.now().plusHours(2L);
        when(eventRepository.findByAdminParams(null, null, null, start, end, EwmPageRequest.of(0, 10))).thenReturn(events);

        assertEquals(events, eventService.findByAdminParams(null, null, null, start, end, 0, 10));
    }
}