package ru.practicum.ewm.main.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.compilation.entity.Compilation;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Query(" select c " +
            "  from Compilation c " +
            "  left join fetch c.events" +
            " where :pinned is null or c.pinned = :pinned")
    List<Compilation> findByPinnedWithEvents(Boolean pinned, Pageable pageable);

    @Query(" select c " +
            "  from Compilation c " +
            "  left join fetch c.events " +
            " where c.id = :compId")
    Optional<Compilation> findByIdWithEvents(Long compId);
}
