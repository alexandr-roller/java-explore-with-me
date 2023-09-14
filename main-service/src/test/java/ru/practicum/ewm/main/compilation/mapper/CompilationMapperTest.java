package ru.practicum.ewm.main.compilation.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.category.mapper.CategoryMapperImpl;
import ru.practicum.ewm.main.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.main.compilation.entity.Compilation;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.mapper.EventMapperImpl;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.mapper.UserMapperImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        EventMapperImpl.class,
        CategoryMapperImpl.class,
        UserMapperImpl.class,
        CompilationMapperImpl.class
})
class CompilationMapperTest {
    @Autowired
    private CompilationMapper compilationMapper;

    @Test
    void fromNewDTO() {
        CompilationNewDto dto = CompilationNewDto.builder().title("title").build();
        Compilation entity = compilationMapper.fromNewDTO(dto);

        assertEquals(dto.getTitle(), entity.getTitle());
        assertFalse(entity.getPinned());
    }

    @Test
    void toDTO() {
        LocalDateTime now = LocalDateTime.now();
        Event event = Event
                .builder()
                .id(1L)
                .title("title")
                .annotation("annotation")
                .category(Category.builder().id(1L).name("category").build())
                .initiator(User.builder().id(1L).name("username").email("test@test.com").build())
                .description("description")
                .eventDate(now.plusDays(1))
                .lat(1.1)
                .lon(2.2)
                .paid(true)
                .participantLimit(10)
                .requestModeration(true)
                .createDate(now)
                .state(Event.State.PENDING)
                .build();
        Compilation compilation = Compilation
                .builder()
                .id(1L)
                .title("title")
                .pinned(true)
                .events(List.of(event))
                .build();
        CompilationDto compilationDto = compilationMapper.toDTO(compilation);
        EventShortDto eventDto = compilationDto.getEvents().get(0);

        assertEquals(compilation.getId(), compilationDto.getId());
        assertEquals(compilation.getTitle(), compilationDto.getTitle());
        assertEquals(compilation.getPinned(), compilationDto.getPinned());
        assertEquals(event.getId(), eventDto.getId());
        assertEquals(event.getTitle(), eventDto.getTitle());
        assertEquals(event.getAnnotation(), eventDto.getAnnotation());
        assertEquals(event.getCategory().getId(), eventDto.getCategory().getId());
        assertEquals(event.getCategory().getName(), eventDto.getCategory().getName());
        assertEquals(event.getInitiator().getId(), eventDto.getInitiator().getId());
        assertEquals(event.getInitiator().getName(), eventDto.getInitiator().getName());
        assertEquals(event.getEventDate(), eventDto.getEventDate());
        assertEquals(event.getPaid(), eventDto.getPaid());
    }
}