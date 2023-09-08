package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.dto.ViewStatsDto;
import ru.practicum.ewm.stats.entity.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query(" select new ru.practicum.ewm.stats.dto.ViewStatsDto(eh.app, eh.uri, count(eh.ip) as hits) " +
            "  from EndpointHit eh" +
            " where eh.timestamp between :start and :end " +
            "   and (coalesce(:uris) is null or eh.uri in (:uris)) " +
            " group by eh.app, eh.uri" +
            " order by hits desc")
    List<ViewStatsDto> findStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(" select new ru.practicum.ewm.stats.dto.ViewStatsDto(eh.app, eh.uri, count(distinct eh.ip) as hits) " +
            "  from EndpointHit eh" +
            " where eh.timestamp between :start and :end " +
            "   and (coalesce(:uris) is null or eh.uri in (:uris)) " +
            " group by eh.app, eh.uri" +
            " order by hits desc")
    List<ViewStatsDto> findStatsUniqueIP(LocalDateTime start, LocalDateTime end, List<String> uris);
}
