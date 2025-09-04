package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.JobRepository;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

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

    // NOVO: Método para marcar um Job como concluído
    public Job completeJob(UUID jobId, User user) {
        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new IllegalArgumentException("Job não encontrado."));

        // Apenas o contratante ou o freelancer pode marcar o Job como concluído
        if (!job.getContractor().getId().equals(user.getId()) && !job.getFreelancer().getId().equals(user.getId())) {
            throw new IllegalStateException("Apenas os participantes do job podem marcá-lo como concluído.");
        }

        job.setStatus(Job.Status.completed);
        job.setCompletedAt(OffsetDateTime.now());

        return jobRepository.save(job);
    }
}