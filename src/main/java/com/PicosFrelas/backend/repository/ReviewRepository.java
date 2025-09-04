package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}