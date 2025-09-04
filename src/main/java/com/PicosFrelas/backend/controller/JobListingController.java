package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.JobListing;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.JobListingRepository;
import com.PicosFrelas.backend.service.JobListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/job-listings")
public class JobListingController {

    private final JobListingService jobListingService;
    private final JobListingRepository jobListingRepository;

    public JobListingController(JobListingService jobListingService, JobListingRepository jobListingRepository) {
        this.jobListingService = jobListingService;
        this.jobListingRepository = jobListingRepository;
    }

    @PostMapping
    public ResponseEntity<JobListing> createJobListing(@RequestBody JobListing jobListing, @AuthenticationPrincipal User creator) {
        jobListing.setCreator(creator);
        JobListing createdListing = jobListingService.createJobListing(jobListing);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdListing);
    }

    //Endpoint para listar vagas com filtros (acesso público)
    @GetMapping
    public ResponseEntity<List<JobListing>> getAllJobListings(@RequestParam(required = false) String city,
                                                              @RequestParam(required = false) String query) {
        List<JobListing> listings = jobListingRepository.findByFilters(city, query);
        return ResponseEntity.ok(listings);
    }
}