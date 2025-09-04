package com.PicosFrelas.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "started_at")
    private OffsetDateTime startedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @OneToOne
    @JoinColumn(name = "gig_id", nullable = false)
    private Gig gig;

    @OneToOne
    @JoinColumn(name = "proposal_id", nullable = false)
    private Proposal proposal;

    @ManyToOne
    @JoinColumn(name = "contractor_id", nullable = false)
    private User contractor;

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public enum Status {
        started, completed, closed, disputed
    }

    @PrePersist
    protected void onCreate() {
        startedAt = OffsetDateTime.now();
        if (status == null) {
            status = Status.started;
        }
    }
}