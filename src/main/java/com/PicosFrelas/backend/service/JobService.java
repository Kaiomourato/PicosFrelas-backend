package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.JobRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findJobsByContractor(User contractor) {
        return jobRepository.findByContractorId(contractor.getId());
    }

    public List<Job> findJobsByFreelancer(User freelancer) {
        return jobRepository.findByFreelancerId(freelancer.getId());
    }
}