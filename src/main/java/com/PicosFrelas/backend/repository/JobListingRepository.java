package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.JobListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface JobListingRepository extends JpaRepository<JobListing, UUID> {
    List<JobListing> findByCityIgnoreCase(String city);
}
