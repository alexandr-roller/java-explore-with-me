package ru.practicum.ewm.main.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main.event.dto.validator.EventDate;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventUpdateAdminDto {
    @Setter
    private Long id;

    @Length(min = 3, max = 120)
    private String title;

    @Length(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Length(min = 20, max = 7000)
    private String description;

    @EventDate
    private LocalDateTime eventDate;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private LocationDto location;

    private StateAction stateAction;

    public enum StateAction {
        PUBLISH_EVENT, REJECT_EVENT
    }
}
