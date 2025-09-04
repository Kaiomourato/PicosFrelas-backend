package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface JobListingRepository extends JpaRepository<JobListing, UUID> {

    // Mantém o método de busca por cidade
    List<JobListing> findByCityIgnoreCase(String city);

    // Método para buscar por múltiplos filtros
    @Query("SELECT j FROM JobListing j WHERE " +
           "(:city IS NULL OR j.city = :city) AND " +
           "(:query IS NULL OR " +
           "LOWER(j.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(j.company) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<JobListing> findByFilters(@Param("city") String city,
                                   @Param("query") String query);
}