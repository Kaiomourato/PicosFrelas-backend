package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.Proposal;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.service.GigService;
import com.PicosFrelas.backend.service.ProposalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProposalController {

    private final ProposalService proposalService;
    private final GigService gigService;

    public ProposalController(ProposalService proposalService, GigService gigService) {
        this.proposalService = proposalService;
        this.gigService = gigService;
    }

    @PostMapping("/gigs/{gigId}/proposals")
    public ResponseEntity<Proposal> createProposal(@PathVariable UUID gigId, @RequestBody Proposal proposal, @AuthenticationPrincipal User freelancer) {
        try {
            Proposal createdProposal = proposalService.createProposal(gigId, proposal, freelancer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProposal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/proposals/{proposalId}/accept")
    public ResponseEntity<?> acceptProposal(@PathVariable UUID proposalId, @AuthenticationPrincipal User contractor) {
        try {
            Job newJob = proposalService.acceptProposal(proposalId, contractor);
            return ResponseEntity.ok(newJob);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }

    // NOVO: Endpoint para listar propostas por gig (protegido)
    @GetMapping("/gigs/{gigId}/proposals")
    public ResponseEntity<List<Proposal>> getProposalsByGig(@PathVariable UUID gigId, @AuthenticationPrincipal User user) {
        Optional<Gig> gigOptional = gigService.findGigById(gigId);
        if (gigOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Gig gig = gigOptional.get();
        if (!gig.getCreator().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(gig.getProposals());
    }
}