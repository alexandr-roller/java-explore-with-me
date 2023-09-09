package ru.practicum.ewm.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.common.dto.EndpointHitDto;
import ru.practicum.ewm.common.dto.ViewStatsDto;
import ru.practicum.ewm.common.validator.RequestRange;
import ru.practicum.ewm.stats.mapper.EndpointHitMapper;
import ru.practicum.ewm.stats.service.EndpointHitService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.common.CommonFormats.DATE_TIME_FORMAT;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EndpointHitController {
    private final EndpointHitService hitService;
    private final EndpointHitMapper hitMapper;

    @PostMapping(value = "/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@Valid @RequestBody EndpointHitDto hitDto) {
        log.info("Post hit uri={}, ip={}, app={}", hitDto.getUri(), hitDto.getIp(), hitDto.getApp());
        hitService.saveHit(hitMapper.fromDTO(hitDto));
    }

    @GetMapping(value = "/stats")
    @RequestRange
    public List<ViewStatsDto> getStats(
            @RequestParam @NotNull @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
            @RequestParam @NotNull @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
            @RequestParam @Nullable List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique
    ) {
        log.info("Get stats, start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        return hitService.getStats(start, end, uris, unique);
    }

}
