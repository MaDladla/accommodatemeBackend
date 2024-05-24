package com.codemasters.accommodateme.repository.repos;
import com.codemasters.accommodateme.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review save(Review review);
    List<Review> findAll();

}
