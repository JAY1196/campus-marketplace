// Path: src/main/java/com/campus/marketplace/dto/ProfileUpdateRequest.java

package com.campus.marketplace.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateRequest {

    private String name;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    private String contactNumber;

    private String avatarUrl;
}