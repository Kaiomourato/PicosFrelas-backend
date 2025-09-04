package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.Gig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface GigRepository extends JpaRepository<Gig, UUID> {

    @Query("SELECT g FROM Gig g WHERE " +
           "(:category IS NULL OR g.category = :category) AND " +
           "(:locationCity IS NULL OR g.locationCity = :locationCity) AND " +
           "(:minPrice IS NULL OR g.budgetMax >= :minPrice) AND " +
           "(:maxPrice IS NULL OR g.budgetMin <= :maxPrice)")
    List<Gig> findByFilters(@Param("category") String category,
                            @Param("locationCity") String locationCity,
                            @Param("minPrice") BigDecimal minPrice,
                            @Param("maxPrice") BigDecimal maxPrice);
}