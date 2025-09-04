package com.PicosFrelas.backend.repository;

import com.PicosFrelas.backend.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProposalRepository extends JpaRepository<Proposal, UUID> {
    List<Proposal> findByFreelancerId(UUID freelancerId);
}