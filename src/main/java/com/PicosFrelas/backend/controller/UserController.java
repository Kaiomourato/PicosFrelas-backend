package com.PicosFrelas.backend.controller;

import com.PicosFrelas.backend.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public User getAuthenticatedUser(@AuthenticationPrincipal User user) {
        return user;
    }
}