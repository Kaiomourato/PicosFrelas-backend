package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/temp")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin() {
        if (userRepository.findByEmail("kaiomouratodemoura@gmail.com").isPresent()) {
            return ResponseEntity.badRequest().body("Admin user already exists.");
        }
        User admin = new User();
        admin.setName("Kaio Mourato");
        admin.setEmail("kaiomouratodemoura@gmail.com");
        admin.setPasswordHash(passwordEncoder.encode("admin123")); // Senha aqui
        admin.setIsAdmin(true);
        userRepository.save(admin);
        return ResponseEntity.ok("Admin user created successfully.");
    }
}