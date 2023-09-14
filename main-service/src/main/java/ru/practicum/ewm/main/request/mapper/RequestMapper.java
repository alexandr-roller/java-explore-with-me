package ru.practicum.ewm.main.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.dto.RequestUpdateStatusResultDto;
import ru.practicum.ewm.main.request.entity.Request;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(source = "createDate", target = "created")
    @Mapping(source = "requesterId", target = "requester")
    @Mapping(source = "eventId", target = "event")
    RequestDto toDTO(Request request);

    List<RequestDto> toDTOs(List<Request> requests);

    default RequestUpdateStatusResultDto toUpdateStatusResultDTO(List<Request> requests) {
        return RequestUpdateStatusResultDto
                .builder()
                .confirmedRequests(
                        toDTOs(requests
                                .stream()
                                .filter(r -> r.getStatus().equals(Request.Status.CONFIRMED))
                                .collect(Collectors.toList()))
                )
                .rejectedRequests(
                        toDTOs(requests
                                .stream()
                                .filter(r -> r.getStatus().equals(Request.Status.REJECTED))
                                .collect(Collectors.toList()))
                )
                .build();
    }
}
