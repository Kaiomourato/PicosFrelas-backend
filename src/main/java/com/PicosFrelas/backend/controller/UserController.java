package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.Review;
import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.ReviewRepository;
import com.PicosFrelas.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder; // Importe esta
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Adicione esta linha

    public UserController(ReviewRepository reviewRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PutMapping("/{id}/toggle-partner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> togglePartnerStatus(@PathVariable UUID id) {
        return userRepository.findById(id).map(user -> {
            user.setIsPartner(!user.getIsPartner());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Método temporário para criar um usuário administrador
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin() {
        if (userRepository.findByEmail("kaiomouratodemoura@gmail.com").isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Admin user already exists."));
        }

        User admin = new User();
        admin.setName("Kaio Mourato");
        admin.setEmail("kaiomouratodemoura@gmail.com");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setIsAdmin(true);

        userRepository.save(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Admin user created successfully."));
    }
}