package ru.practicum.ewm.main.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main.category.dto.CategoryDto;
import ru.practicum.ewm.main.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventFullDto {
    private Long id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private UserShortDto initiator;
    private String description;
    private LocalDateTime eventDate;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private LocationDto location;
    private Integer confirmedRequests;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;
    private String state;
    @Builder.Default
    @Setter
    private Long views = 0L;
}
