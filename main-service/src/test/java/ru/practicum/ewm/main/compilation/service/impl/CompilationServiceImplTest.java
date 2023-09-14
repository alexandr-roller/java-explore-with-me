package ru.practicum.ewm.main.compilation.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.compilation.entity.Compilation;
import ru.practicum.ewm.main.compilation.repository.CompilationRepository;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.repository.EventRepository;
import ru.practicum.ewm.main.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompilationServiceImplTest {
    @Mock
    private CompilationRepository compilationRepository;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private CompilationServiceImpl compilationService;


    @Test
    void shouldCreate() {
        List<Event> events = List.of(Event
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
                .build());
        Compilation compilation = Compilation.builder().id(1L).title("title").pinned(true).build();
        List<Long> eventIds = List.of(1L);
        when(eventRepository.findByIdIn(eventIds)).thenReturn(events);
        when(compilationRepository.save(any())).thenReturn(compilation.withEvents(events));
        Compilation actual = compilationService.create(compilation, eventIds);

        assertNotNull(actual);
        assertNotNull(actual.getEvents());
    }

    @Test
    void shouldUpdate() {
        Compilation compilation = Compilation.builder().id(1L).title("title").pinned(true).build();
        Compilation compilationForUpdate = Compilation.builder().id(1L).title("new title").pinned(true).build();
        when(compilationRepository.findByIdWithEvents(1L)).thenReturn(Optional.ofNullable(compilation));
        when(compilationRepository.save(any())).thenReturn(compilationForUpdate);

        assertEquals(compilationForUpdate, compilationService.update(compilationForUpdate, null));
    }

    @Test
    void deleteById() {
        long id = 1L;
        compilationService.deleteById(id);

        verify(compilationRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void findById() {
        Compilation compilation = Compilation.builder().id(1L).title("title").pinned(true).build();
        when(compilationRepository.findByIdWithEvents(1L)).thenReturn(Optional.ofNullable(compilation));

        assertEquals(compilation, compilationService.findById(1L));
    }
}