package com.PicosFrelas.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "proposals")
@Data
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String message;

    @Column(name = "proposed_value")
    private BigDecimal proposedValue;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "gig_id", nullable = false)
    private Gig gig;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer;

    public enum Status {
        pending, accepted, rejected
    }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        if (status == null) {
            status = Status.pending;
        }
    }
}