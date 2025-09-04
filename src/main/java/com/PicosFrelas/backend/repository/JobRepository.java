package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findByContractorId(UUID contractorId);
    List<Job> findByFreelancerId(UUID freelancerId);
}