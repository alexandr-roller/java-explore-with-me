package ru.practicum.ewm.main.event.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventShortDto {
    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private UserShortDto initiator;
    private LocalDateTime eventDate;
    private Boolean paid;
    private Integer confirmedRequests;
    @Builder.Default
    private Long views = 0L;
}
