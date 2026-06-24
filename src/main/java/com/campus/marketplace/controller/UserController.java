// Path: src/main/java/com/campus/marketplace/controller/UserController.java

package com.campus.marketplace.controller;

import com.campus.marketplace.dto.ProfileResponse;
import com.campus.marketplace.dto.ProfileUpdateRequest;
import com.campus.marketplace.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/users/me — logged-in user's own profile
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getMyProfile(authentication.getName()));
    }

    // PUT /api/users/me — update own profile
    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateProfile(
            @Valid @RequestBody ProfileUpdateRequest request,
            Authentication authentication) {

        return ResponseEntity.ok(userService.updateProfile(authentication.getName(), request));
    }

    // GET /api/users/{id} — public profile view (no auth needed)
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getPublicProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getPublicProfile(id));
    }
}