package ru.practicum.ewm.stats.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.dto.ViewStatsDto;
import ru.practicum.ewm.stats.entities.EndpointHit;
import ru.practicum.ewm.stats.repository.EndpointHitRepository;
import ru.practicum.ewm.stats.service.EndpointHitService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository hitRepository;

    @Override
    @Transactional
    public void saveHit(EndpointHit hit) {
        hitRepository.save(hit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (unique) {
            return hitRepository.findStatsUniqueIP(start, end, uris);
        } else {
            return hitRepository.findStats(start, end, uris);
        }
    }
}
