package ru.practicum.ewm.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.common.validator.RequestRange;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventUpdateAdminDto;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.event.mapper.EventMapper;
import ru.practicum.ewm.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.common.CommonFormats.DATE_TIME_FORMAT;

@RestController
@RequestMapping(value = "/admin/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventAdminController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    @RequestRange
    public List<EventFullDto> get(
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<Event.State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get events by parameters");
        return eventMapper.toFullDTOs(
                eventService.findByAdminParams(users, states, categories, rangeStart, rangeEnd, from, size)
        );
    }

    @PatchMapping(value = "/{eventId}")
    public EventFullDto update(
            @Valid @RequestBody EventUpdateAdminDto eventDto,
            @PathVariable Long eventId
    ) {
        log.info("Patch event eventId={}", eventId);
        eventDto.setId(eventId);
        return eventMapper.toFullDTO(
                eventService.updateByAdmin(eventMapper.fromUpdateAdminDTO(eventDto))
        );
    }
}
