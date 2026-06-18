// Path: src/main/java/com/campus/marketplace/entity/User.java

package com.campus.marketplace.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;  // stored as BCrypt hash — never plain text

    @Enumerated(EnumType.STRING)
    private Role role;        // STUDENT or ADMIN

    public enum Role {
        STUDENT, ADMIN
    }
}