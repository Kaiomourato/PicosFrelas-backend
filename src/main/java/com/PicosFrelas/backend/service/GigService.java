package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.Gig;
import com.PicosFrelas.backend.repository.GigRepository;
import org.springframework.stereotype.Service;

@Service
public class GigService {

    private final GigRepository gigRepository;

    public GigService(GigRepository gigRepository) {
        this.gigRepository = gigRepository;
    }

    public Gig createGig(Gig gig) {
        // Lógica de validação pode vir aqui
        return gigRepository.save(gig);
    }
}