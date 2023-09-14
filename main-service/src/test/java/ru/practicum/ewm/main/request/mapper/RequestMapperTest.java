package ru.practicum.ewm.main.request.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.entity.Request;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RequestMapperTest {
    private final RequestMapper requestMapper = Mappers.getMapper(RequestMapper.class);

    @Test
    void toDTO() {
        Request entity = Request
                .builder()
                .id(1L)
                .requesterId(1L)
                .eventId(1L)
                .createDate(LocalDateTime.now())
                .status(Request.Status.PENDING)
                .build();
        RequestDto dto = requestMapper.toDTO(entity);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getRequesterId(), dto.getRequester());
        assertEquals(entity.getEventId(), dto.getEvent());
        assertEquals(entity.getCreateDate(), dto.getCreated());
        assertEquals(entity.getStatus().name(), dto.getStatus());
    }
}