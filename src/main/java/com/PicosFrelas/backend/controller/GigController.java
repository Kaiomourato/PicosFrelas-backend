package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.dto.GigResponseDto;
import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.GigRepository;
import com.PicosFrelas.backend.service.GigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gigs")
public class GigController {

    private final GigService gigService;
    private final GigRepository gigRepository; // Adicione o repositório

    public GigController(GigService gigService, GigRepository gigRepository) {
        this.gigService = gigService;
        this.gigRepository = gigRepository;
    }

    @PostMapping
    public ResponseEntity<GigResponseDto> createGig(@RequestBody Gig gig, @AuthenticationPrincipal User creator) {
        gig.setCreator(creator);
        Gig createdGig = gigService.createGig(gig);
        return ResponseEntity.status(HttpStatus.CREATED).body(GigResponseDto.fromEntity(createdGig));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GigResponseDto> getGigById(@PathVariable UUID id) {
        return gigService.findGigById(id)
                .map(gig -> ResponseEntity.ok(GigResponseDto.fromEntity(gig)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Método que lida com a busca por filtros
    @GetMapping
    public ResponseEntity<List<GigResponseDto>> getGigsByFilters(@RequestParam(required = false) String category,
                                                                 @RequestParam(required = false) String locationCity,
                                                                 @RequestParam(required = false) BigDecimal minPrice,
                                                                 @RequestParam(required = false) BigDecimal maxPrice) {
        List<Gig> gigs = gigRepository.findByFilters(category, locationCity, minPrice, maxPrice);
        
        List<GigResponseDto> gigDtos = gigs.stream()
            .map(GigResponseDto::fromEntity)
            .collect(Collectors.toList());
            
        return ResponseEntity.ok(gigDtos);
    }
}