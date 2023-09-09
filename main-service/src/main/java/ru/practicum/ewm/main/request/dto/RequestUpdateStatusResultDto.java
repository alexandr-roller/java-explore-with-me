package ru.practicum.ewm.main.request.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RequestUpdateStatusResultDto {
    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}
