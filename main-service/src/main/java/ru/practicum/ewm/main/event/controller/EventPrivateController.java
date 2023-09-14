package ru.practicum.ewm.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventNewDto;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.dto.EventUpdatePrivateDto;
import ru.practicum.ewm.main.event.mapper.EventMapper;
import ru.practicum.ewm.main.event.service.EventService;
import ru.practicum.ewm.main.request.dto.RequestDto;
import ru.practicum.ewm.main.request.dto.RequestUpdateStatusDto;
import ru.practicum.ewm.main.request.dto.RequestUpdateStatusResultDto;
import ru.practicum.ewm.main.request.entity.Request;
import ru.practicum.ewm.main.request.mapper.RequestMapper;
import ru.practicum.ewm.main.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(value = "/users/{userId}/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventPrivateController {
    private final EventService eventService;
    private final RequestService requestService;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;

    @GetMapping
    public List<EventFullDto> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get events by userId={}", userId);
        return eventMapper.toFullDTOs(eventService.findByInitiatorId(userId, from, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(
            @Valid @RequestBody EventNewDto eventDto,
            @PathVariable Long userId
    ) {
        log.info("Post event");
        return eventMapper.toFullDTO(
                eventService.create(eventMapper.fromNewDTO(eventDto), userId, eventDto.getCategory())
        );
    }

    @GetMapping(value = "/{eventId}")
    public EventFullDto getByUserIdAndEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get events by userId={}", userId);
        return eventMapper.toFullDTO(eventService.findByIdAndInitiatorId(eventId, userId));
    }

    @PatchMapping(value = "/{eventId}")
    public EventFullDto update(
            @Valid @RequestBody EventUpdatePrivateDto eventDto,
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Patch event");
        eventDto.setId(eventId);
        return eventMapper.toFullDTO(
                eventService.updateByUser(eventMapper.fromUpdatePrivateDTO(eventDto), userId)
        );
    }

    @GetMapping(value = "/{eventId}/requests")
    public List<RequestDto> getRequestsByEventId(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Get requests by eventId={}, userId={}", eventId, userId);
        return requestMapper.toDTOs(requestService.findByUserIdAndEventId(userId, eventId));
    }

    @PatchMapping(value = "/{eventId}/requests")
    public RequestUpdateStatusResultDto updateRequests(
            @Valid @RequestBody RequestUpdateStatusDto dto,
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Patch requests");
        return requestMapper.toUpdateStatusResultDTO(
                requestService.updateStatus(userId, eventId, dto.getRequestIds(), Request.Status.valueOf(dto.getStatus().name()))
        );
    }

    @GetMapping(value = "/subscriptions")
    public List<EventShortDto> getSubscriptionEvents(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get events by subsId={}", userId);
        return eventMapper.toShortDTOs(eventService.findBySubscriberId(userId, from, size));
    }
}
