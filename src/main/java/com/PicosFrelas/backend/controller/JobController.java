package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.Review;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.service.JobService;
import com.PicosFrelas.backend.service.ReviewService; // Importe
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final ReviewService reviewService; 

    public JobController(JobService jobService, ReviewService reviewService) { // Adicione a dependência
        this.jobService = jobService;
        this.reviewService = reviewService;
    }

    // Endpoint de listagem de jobs (acesso protegido)
    @GetMapping
    public ResponseEntity<List<Job>> getJobs(@RequestParam(required = true) String role, @AuthenticationPrincipal User user) {
        if ("freelancer".equalsIgnoreCase(role)) {
            List<Job> jobs = jobService.findJobsByFreelancer(user);
            return ResponseEntity.ok(jobs);
        } else if ("contractor".equalsIgnoreCase(role)) {
            List<Job> jobs = jobService.findJobsByContractor(user);
            return ResponseEntity.ok(jobs);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //Endpoint para criar uma avaliação em um job (PROTEGIDO)
    @PostMapping("/{jobId}/reviews")
    public ResponseEntity<?> createReview(@PathVariable UUID jobId, @RequestBody Review review, @AuthenticationPrincipal User reviewer) {
        try {
            Review createdReview = reviewService.createReview(jobId, review, reviewer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{jobId}/complete")
    public ResponseEntity<?> completeJob(@PathVariable UUID jobId, @AuthenticationPrincipal User user) {
        try {
            Job completedJob = jobService.completeJob(jobId, user);
            return ResponseEntity.ok(completedJob);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}