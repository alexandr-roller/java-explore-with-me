package ru.practicum.ewm.main.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.event.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIdIn(List<Long> eventIds);

    Optional<Event> findByIdAndState(Long id, Event.State state);

    List<Event> findByInitiatorIdOrderById(Long initiatorId, Pageable pageable);

    List<Event> findByInitiatorIdInAndState(List<Long> initiatorIds, Event.State state, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

    @Query(" select e " +
            "  from Event e " +
            " where (coalesce(:userIds) is null or e.initiator.id in (:userIds)) " +
            "   and (coalesce(:states) is null or e.state in (:states)) " +
            "   and (coalesce(:categoryIds) is null or e.category.id in (:categoryIds)) " +
            "   and (e.eventDate between :start and :end) "
    )
    List<Event> findByAdminParams(
            List<Long> userIds,
            List<Event.State> states,
            List<Long> categoryIds,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );


    @Query(" select e " +
            "  from Event e " +
            " where (:text is null or upper(e.annotation) like upper(concat('%',:text,'%'))) " +
            "   and (coalesce(:categoryIds) is null or e.category.id in (:categoryIds)) " +
            "   and (e.eventDate between :start and :end) " +
            "   and (:paid is null or e.paid = :paid) " +
            "   and (:onlyAvailable is null or e.participantLimit = 0 or" +
            "           (e.participantLimit > 0 and (select count(r.id) from Request r where r.status = 2) < e.participantLimit)" +
            "       )" +
            "   and e.state = 2"
    )
    List<Event> findByPublicParams(
            String text,
            List<Long> categoryIds,
            Boolean paid,
            LocalDateTime start,
            LocalDateTime end,
            Boolean onlyAvailable,
            Pageable pageable
    );
}
