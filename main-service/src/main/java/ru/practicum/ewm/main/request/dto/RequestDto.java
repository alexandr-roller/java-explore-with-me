package ru.practicum.ewm.main.request.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RequestDto {
    private Long id;
    private Long event;
    private Long requester;
    private String status;
    private LocalDateTime created;
}
