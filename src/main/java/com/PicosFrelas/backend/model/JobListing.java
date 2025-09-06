package com.PicosFrelas.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_listings")
@Data
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // NOVO: Campo para o tipo de anúncio
    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;
    private String company;
    private String description;
    private String city;

    @Column(name = "contact_info")
    private String contactInfo;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public enum Type {
        JOB, BUSINESS, EVENT
    }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}