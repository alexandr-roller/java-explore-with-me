package ru.practicum.ewm.main.event.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocationDto {
    private Double lat;
    private Double lon;
}
