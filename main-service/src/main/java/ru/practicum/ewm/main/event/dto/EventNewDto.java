package ru.practicum.ewm.main.event.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main.event.dto.validator.EventDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class EventNewDto {
    @NotBlank
    @Length(min = 3, max = 120)
    private String title;

    @NotBlank
    @Length(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private Long category;

    @NotBlank
    @Length(min = 20, max = 7000)
    private String description;

    @EventDate
    private LocalDateTime eventDate;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private LocationDto location;
}
