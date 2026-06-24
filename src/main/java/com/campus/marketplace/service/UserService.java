// Path: src/main/java/com/campus/marketplace/service/UserService.java

package com.campus.marketplace.service;

import com.campus.marketplace.dto.ProfileResponse;
import com.campus.marketplace.dto.ProfileUpdateRequest;
import com.campus.marketplace.entity.User;
import com.campus.marketplace.exception.ResourceNotFoundException;
import com.campus.marketplace.repository.ProductRepository;
import com.campus.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // GET own profile (from JWT)
    public ProfileResponse getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        long activeListings = productRepository.findBySeller(user)
                .stream()
                .filter(p -> !p.getIsSold())
                .count();

        return ProfileResponse.fromEntity(user, activeListings);
    }

    // GET any user's public profile by id
    public ProfileResponse getPublicProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        long activeListings = productRepository.findBySeller(user)
                .stream()
                .filter(p -> !p.getIsSold())
                .count();

        return ProfileResponse.fromEntity(user, activeListings);
    }

    // UPDATE own profile
    public ProfileResponse updateProfile(String email, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }
        if (request.getContactNumber() != null) {
            user.setContactNumber(request.getContactNumber());
        }
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
        }

        User updated = userRepository.save(user);

        long activeListings = productRepository.findBySeller(updated)
                .stream()
                .filter(p -> !p.getIsSold())
                .count();

        return ProfileResponse.fromEntity(updated, activeListings);
    }
}