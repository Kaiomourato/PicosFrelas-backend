package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.repository.GigRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Service
public class GigService {

    private final GigRepository gigRepository;

    public GigService(GigRepository gigRepository) {
        this.gigRepository = gigRepository;
    }

    public Gig createGig(Gig gig) {
        return gigRepository.save(gig);
    }

    
    public List<Gig> findAllGigs() {
        return gigRepository.findAll();
    }

    
    public Optional<Gig> findGigById(UUID id) {
        return gigRepository.findById(id);
    }
}