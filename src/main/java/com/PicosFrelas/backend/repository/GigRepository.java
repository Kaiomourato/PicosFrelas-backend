package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface GigRepository extends JpaRepository<Gig, UUID> {
}