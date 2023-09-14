package ru.practicum.ewm.main.stats.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.common.dto.ViewStatsDto;
import ru.practicum.ewm.main.event.entity.Event;
import ru.practicum.ewm.main.stats.service.StatsService;
import ru.practicum.ewm.stats.httpclient.StatsClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsClient statsClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void loadViews(List<Event> events) {
        if (events.size() == 0) {
            return;
        }

        LocalDateTime start = events
                .stream()
                .map(Event::getCreateDate)
                .min(LocalDateTime::compareTo)
                .get();

        LocalDateTime end = LocalDateTime.now();

        List<String> uris = events
                .stream()
                .map(e -> "/events/" + e.getId())
                .collect(Collectors.toList());
        boolean unique = true;

        log.info("Load stats start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);
        log.info(response.getStatusCode().toString());

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                List<ViewStatsDto> views = Arrays.asList(
                        mapper.readValue(
                                mapper.writeValueAsString(response.getBody()), ViewStatsDto[].class
                        )
                );
                Map<Long, Long> mapViews = views
                        .stream()
                        .collect(Collectors.toMap(
                                v -> {
                                    String[] str = v.getUri().split("/");
                                    return Long.valueOf(str[2]);
                                },
                                ViewStatsDto::getHits
                        ));
                log.info("Stats for uris={}: {}", uris, views);
                events.forEach(e -> e.setViews(mapViews.getOrDefault(e.getId(), 0L)));
            } catch (JsonProcessingException ex) {
                log.warn("Json processing exception");
            }
        }
    }

    @Override
    public void loadViews(Event event) {
        loadViews(List.of(event));
    }
}
