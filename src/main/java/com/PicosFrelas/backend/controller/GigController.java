package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.dto.GigResponseDto;
import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.service.GigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gigs")
public class GigController {

    private final GigService gigService;

    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    @PostMapping
    public ResponseEntity<GigResponseDto> createGig(@RequestBody Gig gig, @AuthenticationPrincipal User creator) {
        gig.setCreator(creator);
        Gig createdGig = gigService.createGig(gig);
        return ResponseEntity.status(HttpStatus.CREATED).body(GigResponseDto.fromEntity(createdGig));
    }

    
    @GetMapping
    public ResponseEntity<List<GigResponseDto>> getAllGigs() {
        List<GigResponseDto> gigs = gigService.findAllGigs().stream()
            .map(GigResponseDto::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(gigs);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<GigResponseDto> getGigById(@PathVariable UUID id) {
        return gigService.findGigById(id)
            .map(gig -> ResponseEntity.ok(GigResponseDto.fromEntity(gig)))
            .orElse(ResponseEntity.notFound().build());
    }
}