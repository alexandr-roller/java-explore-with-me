package ru.practicum.ewm.main.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.main.event.dto.EventShortDto;

import java.util.List;

@Getter
@Builder
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventShortDto> events;
}
