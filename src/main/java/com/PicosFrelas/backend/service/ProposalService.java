package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.model.Proposal;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.GigRepository;
import com.PicosFrelas.backend.repository.ProposalRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final GigRepository gigRepository;

    public ProposalService(ProposalRepository proposalRepository, GigRepository gigRepository) {
        this.proposalRepository = proposalRepository;
        this.gigRepository = gigRepository;
    }

    public Proposal createProposal(UUID gigId, Proposal proposal, User freelancer) {
        Gig gig = gigRepository.findById(gigId)
            .orElseThrow(() -> new IllegalArgumentException("Gig não encontrado."));

        if (!freelancer.getIsFreelancer()) {
            throw new IllegalStateException("Apenas freelancers podem enviar propostas.");
        }

        proposal.setGig(gig);
        proposal.setFreelancer(freelancer);
        return proposalRepository.save(proposal);
    }
}