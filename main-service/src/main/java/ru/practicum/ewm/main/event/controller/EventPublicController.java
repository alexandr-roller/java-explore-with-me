package ru.practicum.ewm.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.common.dto.EndpointHitDto;
import ru.practicum.ewm.common.validator.RequestRange;
import ru.practicum.ewm.main.event.dto.EventFullDto;
import ru.practicum.ewm.main.event.dto.EventShortDto;
import ru.practicum.ewm.main.event.mapper.EventMapper;
import ru.practicum.ewm.main.event.service.EventService;
import ru.practicum.ewm.stats.httpclient.StatsClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.common.CommonFormats.DATE_TIME_FORMAT;

@RestController
@RequestMapping(value = "/events")
@RequiredArgsConstructor
@Validated
@Slf4j
public class EventPublicController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final StatsClient statsClient;

    @GetMapping
    @RequestRange
    public List<EventShortDto> get(
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) Boolean onlyAvailable,
            @RequestParam(required = false) EventService.SortType sort,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size,
            HttpServletRequest request
    ) {
        log.info("Get events by parameters");
        List<EventShortDto> dtos = eventMapper.toShortDTOs(
                eventService.findByPublicParams(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size)
        );
        statsClient.hit(EndpointHitDto
                .builder()
                .app("ewm-main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .build());
        return dtos;
    }

    @GetMapping(value = "/{eventId}")
    public EventFullDto getById(
            @PathVariable Long eventId,
            HttpServletRequest request
    ) {
        log.info("Get event eventId={}", eventId);
        EventFullDto dto = eventMapper.toFullDTO(eventService.findByIdPublished(eventId));
        statsClient.hit(EndpointHitDto
                .builder()
                .app("ewm-main-service")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .build());
        return dto;
    }
}
