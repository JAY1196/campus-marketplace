// Path: src/main/java/com/campus/marketplace/dto/AuthResponse.java

package com.campus.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String role;
}