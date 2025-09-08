package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface JobListingRepository extends JpaRepository<JobListing, UUID> {

    // Usando consulta nativa com CAST explícito
    @Query(value = "SELECT * FROM job_listings j WHERE " +
           "(:city IS NULL OR j.city = :city) AND " +
           "(:query IS NULL OR " +
           "LOWER(CAST(j.title AS TEXT)) LIKE LOWER(CONCAT('%', CAST(:query AS TEXT), '%')) OR " +
           "LOWER(CAST(j.company AS TEXT)) LIKE LOWER(CONCAT('%', CAST(:query AS TEXT), '%')) OR " +
           "LOWER(CAST(j.description AS TEXT)) LIKE LOWER(CONCAT('%', CAST(:query AS TEXT), '%')))", 
           nativeQuery = true)
    List<JobListing> findByFilters(@Param("city") String city, @Param("query") String query);
}