// Path: src/main/java/com/campus/marketplace/dto/ProfileResponse.java

package com.campus.marketplace.dto;

import com.campus.marketplace.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String name;
    private String email;
    private String bio;
    private String contactNumber;
    private String avatarUrl;
    private String role;
    private long activeListingsCount;   // how many products this user currently has for sale

    public static ProfileResponse fromEntity(User user, long activeListingsCount) {
        return ProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .contactNumber(user.getContactNumber())
                .avatarUrl(user.getAvatarUrl())
                .role(user.getRole().name())
                .activeListingsCount(activeListingsCount)
                .build();
    }
}