package ru.practicum.ewm.stats.service;

import ru.practicum.ewm.common.dto.ViewStatsDto;
import ru.practicum.ewm.stats.entities.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitService {
    void saveHit(EndpointHit hit);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}
