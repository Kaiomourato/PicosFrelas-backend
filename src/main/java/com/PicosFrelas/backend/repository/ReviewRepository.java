package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByRevieweeId(UUID revieweeId);
}