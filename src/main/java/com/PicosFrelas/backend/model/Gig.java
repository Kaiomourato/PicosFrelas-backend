package com.PicosFrelas.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "gigs")
@Data
@JsonIgnoreProperties({"proposals", "creator"})
public class Gig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private String category;

    @Column(name = "location_city")
    private String locationCity;

    @Column(name = "budget_min")
    private BigDecimal budgetMin;

    @Column(name = "budget_max")
    private BigDecimal budgetMax;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    
    @OneToMany(mappedBy = "gig", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposal> proposals = new ArrayList<>();

    
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    public enum Status {
        open, in_progress, done, closed
    }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        if (status == null) {
            status = Status.open;
        }
    }
}