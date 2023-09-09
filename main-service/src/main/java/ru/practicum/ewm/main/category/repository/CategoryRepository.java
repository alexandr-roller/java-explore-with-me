package ru.practicum.ewm.main.category.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.main.category.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(" select c " +
            "  from Category c" +
            " order by c.id")
    List<Category> findAllOrderById(Pageable pageable);
}
