package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.JobListing;
import com.PicosFrelas.backend.repository.JobListingRepository;
import org.springframework.stereotype.Service;

@Service
public class JobListingService {

    private final JobListingRepository jobListingRepository;

    public JobListingService(JobListingRepository jobListingRepository) {
        this.jobListingRepository = jobListingRepository;
    }

    public JobListing createJobListing(JobListing jobListing) {
        return jobListingRepository.save(jobListing);
    }
}