package ru.practicum.ewm.main.request.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.repository.EventRepository;
import ru.practicum.ewm.main.request.entity.Request;
import ru.practicum.ewm.main.request.repository.RequestRepository;
import ru.practicum.ewm.main.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private RequestServiceImpl requestService;

    @Test
    void shouldCreate() {
        Long requesterId = 1L;
        Long eventId = 2L;
        Event event = Event
                .builder()
                .id(1L)
                .title("title")
                .annotation("annotation")
                .category(Category.builder().id(1L).name("category").build())
                .initiator(User.builder().id(2L).name("username").email("test@test.com").build())
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
        when(requestRepository.findByRequesterIdAndEventId(requesterId, eventId)).thenReturn(new ArrayList<>());
        when(eventRepository.findById(eventId)).thenReturn(Optional.ofNullable(event));
        requestService.create(requesterId, eventId);

        verify(requestRepository, Mockito.times(1)).save(any());
    }

    @Test
    void shouldFindById() {
        Long requestId = 1L;
        Request request = Request
                .builder()
                .id(1L)
                .requesterId(1L)
                .eventId(1L)
                .createDate(LocalDateTime.now())
                .status(Request.Status.PENDING)
                .build();
        when(requestRepository.findById(requestId)).thenReturn(Optional.ofNullable(request));

        assertEquals(request, requestService.findById(requestId));
    }

    @Test
    void shouldFindByRequesterId() {
        Long requesterId = 1L;
        List<Request> requests = List.of(Request
                .builder()
                .id(1L)
                .requesterId(1L)
                .eventId(1L)
                .createDate(LocalDateTime.now())
                .status(Request.Status.PENDING)
                .build());
        when(requestRepository.findByRequesterId(requesterId)).thenReturn(requests);

        assertEquals(requests, requestService.findByRequesterId(requesterId));
    }

    @Test
    void shouldCancel() {
        Long requestId = 1L;
        Long requesterId = 2L;
        Request request = Request
                .builder()
                .id(1L)
                .requesterId(1L)
                .eventId(1L)
                .createDate(LocalDateTime.now())
                .status(Request.Status.PENDING)
                .build();
        when(requestRepository.findByIdAndRequesterId(requestId, requesterId)).thenReturn(Optional.ofNullable(request));
        when(requestRepository.save(any())).thenReturn(request);

        assertEquals(Request.Status.CANCELED, requestService.cancel(requesterId, requestId).getStatus());
    }

    @Test
    void updateStatus() {
        Long userId = 1L;
        Long eventId = 2L;
        List<Long> requestIds = List.of(3L);
        Request.Status status = Request.Status.REJECTED;
        Event event = Event
                .builder()
                .id(2L)
                .title("title")
                .annotation("annotation")
                .category(Category.builder().id(1L).name("category").build())
                .initiator(User.builder().id(2L).name("username").email("test@test.com").build())
                .description("description")
                .eventDate(LocalDateTime.now().plusDays(1))
                .lat(1.1)
                .lon(2.2)
                .paid(true)
                .participantLimit(10)
                .requestModeration(true)
                .createDate(LocalDateTime.now())
                .state(Event.State.PENDING)
                .build();
        List<Request> requests = List.of(Request
                .builder()
                .id(3L)
                .requesterId(1L)
                .eventId(2L)
                .createDate(LocalDateTime.now())
                .status(Request.Status.PENDING)
                .build());
        when(eventRepository.findByIdAndInitiatorId(eventId, userId)).thenReturn(Optional.ofNullable(event));
        when(requestRepository.findByEventId(eventId)).thenReturn(requests);

        assertEquals(status, requestService.updateStatus(userId, eventId, requestIds, status).get(0).getStatus());
    }
}