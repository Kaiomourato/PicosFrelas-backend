package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.service.GigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gigs")
public class GigController {

    private final GigService gigService;

    public GigController(GigService gigService) {
        this.gigService = gigService;
    }

    @PostMapping
    public ResponseEntity<Gig> createGig(@RequestBody Gig gig, @AuthenticationPrincipal User creator) {
        gig.setCreator(creator);
        Gig createdGig = gigService.createGig(gig);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGig);
    }
}