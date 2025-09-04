package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Review;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ReviewRepository reviewRepository;

    public UserController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/me")
    public User getAuthenticatedUser(@AuthenticationPrincipal User user) {
        return user;
    }

   
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable UUID id) {
        List<Review> reviews = reviewRepository.findByRevieweeId(id);
        return ResponseEntity.ok(reviews);
    }
}