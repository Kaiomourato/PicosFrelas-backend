package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Job;
import com.PicosFrelas.backend.model.Proposal;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.service.ProposalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/gigs/{gigId}/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    public ProposalController(ProposalService proposalService) {
        this.proposalService = proposalService;
    }

    @PostMapping
    public ResponseEntity<Proposal> createProposal(@PathVariable UUID gigId, @RequestBody Proposal proposal, @AuthenticationPrincipal User freelancer) {
        try {
            Proposal createdProposal = proposalService.createProposal(gigId, proposal, freelancer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProposal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // NOVO ENDPOINT DE ACEITE DE PROPOSTA
    @PostMapping("/{proposalId}/accept")
    public ResponseEntity<?> acceptProposal(@PathVariable UUID proposalId, @AuthenticationPrincipal User contractor) {
        try {
            Job newJob = proposalService.acceptProposal(proposalId, contractor);
            return ResponseEntity.ok(newJob);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }
}