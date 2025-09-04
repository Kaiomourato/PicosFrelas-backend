package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.Review;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.JobRepository;
import com.PicosFrelas.backend.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final JobRepository jobRepository;

    public ReviewService(ReviewRepository reviewRepository, JobRepository jobRepository) {
        this.reviewRepository = reviewRepository;
        this.jobRepository = jobRepository;
    }

    public Review createReview(UUID jobId, Review review, User reviewer) {
        Job job = jobRepository.findById(jobId)
            .orElseThrow(() -> new IllegalArgumentException("Job não encontrado."));

        // Valida se o Job está concluído
        if (job.getStatus() != Job.Status.completed) {
            throw new IllegalStateException("A avaliação só pode ser feita em jobs concluídos.");
        }

        // Valida se o usuário que está avaliando é um dos participantes do Job
        if (!job.getContractor().getId().equals(reviewer.getId()) && !job.getFreelancer().getId().equals(reviewer.getId())) {
            throw new IllegalStateException("Apenas os participantes do job podem avaliá-lo.");
        }

        // Define quem está avaliando e quem está sendo avaliado
        if (job.getContractor().getId().equals(reviewer.getId())) {
            review.setReviewer(job.getContractor());
            review.setReviewee(job.getFreelancer());
        } else {
            review.setReviewer(job.getFreelancer());
            review.setReviewee(job.getContractor());
        }
        review.setJob(job);

        return reviewRepository.save(review);
    }
}