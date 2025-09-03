package com.PicosFrelas.backend.service;

import com.PicosFrelas.backend.model.User;
import com.PicosFrelas.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}