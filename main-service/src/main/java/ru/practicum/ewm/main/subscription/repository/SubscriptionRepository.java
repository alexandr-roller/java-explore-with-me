package ru.practicum.ewm.main.subscription.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.subscription.entity.Subscription;
import ru.practicum.ewm.main.user.entity.User;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Modifying
    void deleteBySubsIdAndUserId(Long subsId, Long userId);

    boolean existsBySubsIdAndUserId(Long subsId, Long userId);

    @Query(" select u " +
            "  from User u " +
            "  join Subscription s " +
            "    on s.subsId = u.id " +
            " where s.userId = :userId")
    List<User> findSubscribersByUserId(Long userId, Pageable pageable);

    @Query(" select u " +
            "  from User u " +
            "  join Subscription s " +
            "    on s.userId = u.id " +
            " where s.subsId = :subsId")
    List<User> findSubscriptionsBySubsId(Long subsId);

    @Query(" select u " +
            "  from User u " +
            "  join Subscription s " +
            "    on s.userId = u.id " +
            " where s.subsId = :subsId")
    List<User> findSubscriptionsBySubsId(Long subsId, Pageable pageable);

    List<Subscription> findBySubsId(Long subsId);
}
