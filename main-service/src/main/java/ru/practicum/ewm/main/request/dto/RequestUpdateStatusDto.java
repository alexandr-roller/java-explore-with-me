package ru.practicum.ewm.main.request.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RequestUpdateStatusDto {
    private List<Long> requestIds;
    private Status status;

    public enum Status {
        CONFIRMED, REJECTED
    }
}
