package ru.practicum.ewm.main.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.user.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u " +
            " from User u " +
            " where coalesce(:ids) is null or u.id in (:ids) " +
            " order by u.id")
    List<User> findByIds(List<Long> ids, Pageable pageable);
}
