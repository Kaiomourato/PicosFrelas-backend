package com.PicosFrelas.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reviews")
@Data
@JsonIgnoreProperties({"job"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int rating; // 1-5 estrelas
    private String comment;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer; // Quem deu a avaliação

    @ManyToOne
    @JoinColumn(name = "reviewee_id", nullable = false)
    private User reviewee; // Quem recebeu a avaliação

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}