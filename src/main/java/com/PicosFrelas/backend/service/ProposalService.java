package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.model.Job; 
import com.PicosFrelas.backend.model.Proposal;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.GigRepository;
import com.PicosFrelas.backend.repository.JobRepository; 
import com.PicosFrelas.backend.repository.ProposalRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.UUID;

@Service
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final GigRepository gigRepository;
    private final JobRepository jobRepository; // Adicione

    public ProposalService(ProposalRepository proposalRepository, GigRepository gigRepository, JobRepository jobRepository) {
        this.proposalRepository = proposalRepository;
        this.gigRepository = gigRepository;
        this.jobRepository = jobRepository;
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

    @Transactional
    public Job acceptProposal(UUID proposalId, User contractor) {
        Proposal proposal = proposalRepository.findById(proposalId)
            .orElseThrow(() -> new IllegalArgumentException("Proposta não encontrada."));

        // Valida se o usuário que está aceitando é o criador do gig
        if (!proposal.getGig().getCreator().getId().equals(contractor.getId())) {
            throw new IllegalStateException("Apenas o criador do gig pode aceitar propostas.");
        }

        // Garante que o gig tem status 'open'
        if (proposal.getGig().getStatus() != Gig.Status.open) {
            throw new IllegalStateException("Este gig não está aberto para novas propostas.");
        }

        // Atualiza o status da proposta e do gig
        proposal.setStatus(Proposal.Status.accepted);
        proposal.getGig().setStatus(Gig.Status.in_progress);

        // Cria o novo job
        Job job = new Job();
        job.setGig(proposal.getGig());
        job.setProposal(proposal);
        job.setContractor(contractor);
        job.setFreelancer(proposal.getFreelancer());

        proposalRepository.save(proposal);
        gigRepository.save(proposal.getGig());

        return jobRepository.save(job);
    }
}