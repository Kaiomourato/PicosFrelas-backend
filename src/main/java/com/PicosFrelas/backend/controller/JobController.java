package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // NOVO: Endpoint para listar jobs de um usuário (freelancer ou contratante)
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
}