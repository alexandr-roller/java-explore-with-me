package ru.practicum.ewm.main.event.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.practicum.ewm.main.category.entity.Category;
import ru.practicum.ewm.main.category.mapper.CategoryMapperImpl;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventNewDto;
import ru.practicum.ewm.main.event.dto.LocationDto;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.user.entity.User;
import ru.practicum.ewm.main.user.mapper.UserMapperImpl;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        EventMapperImpl.class,
        CategoryMapperImpl.class,
        UserMapperImpl.class
})
class EventMapperTest {
    @Autowired
    private EventMapper eventMapper;

    @Test
    void shouldMapFromNewDto() {
        EventNewDto dto = EventNewDto
                .builder()
                .title("title")
                .annotation("annotation")
                .category(1L)
                .description("description")
                .eventDate(LocalDateTime.now())
                .paid(true)
                .participantLimit(10)
                .requestModeration(true)
                .location(LocationDto.builder().lat(1.1).lon(2.2).build())
                .build();
        Event entity = eventMapper.fromNewDTO(dto);

        assertEquals(dto.getTitle(), entity.getTitle());
        assertEquals(dto.getAnnotation(), entity.getAnnotation());
        assertEquals(dto.getDescription(), entity.getDescription());
        assertEquals(dto.getEventDate(), entity.getEventDate());
        assertEquals(dto.getLocation().getLat(), entity.getLat());
        assertEquals(dto.getLocation().getLon(), entity.getLon());
        assertEquals(dto.getPaid(), entity.getPaid());
        assertEquals(dto.getParticipantLimit(), entity.getParticipantLimit());
        assertEquals(dto.getRequestModeration(), entity.getRequestModeration());
    }

    @Test
    void shouldMapToFullDto() {
        LocalDateTime now = LocalDateTime.now();
        Event entity = Event
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
        EventFullDto fullDto = eventMapper.toFullDTO(entity);

        assertEquals(entity.getId(), fullDto.getId());
        assertEquals(entity.getTitle(), fullDto.getTitle());
        assertEquals(entity.getAnnotation(), fullDto.getAnnotation());
        assertEquals(entity.getCategory().getId(), fullDto.getCategory().getId());
        assertEquals(entity.getCategory().getName(), fullDto.getCategory().getName());
        assertEquals(entity.getInitiator().getId(), fullDto.getInitiator().getId());
        assertEquals(entity.getInitiator().getName(), fullDto.getInitiator().getName());
        assertEquals(entity.getDescription(), fullDto.getDescription());
        assertEquals(entity.getEventDate(), fullDto.getEventDate());
        assertEquals(entity.getLat(), fullDto.getLocation().getLat());
        assertEquals(entity.getLon(), fullDto.getLocation().getLon());
        assertEquals(entity.getPaid(), fullDto.getPaid());
        assertEquals(entity.getParticipantLimit(), fullDto.getParticipantLimit());
        assertEquals(entity.getRequestModeration(), fullDto.getRequestModeration());
        assertEquals(entity.getCreateDate(), fullDto.getCreatedOn());
        assertEquals(entity.getState().name(), fullDto.getState());
    }
}