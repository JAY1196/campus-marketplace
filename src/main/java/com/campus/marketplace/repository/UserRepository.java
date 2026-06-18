// Path: src/main/java/com/campus/marketplace/repository/UserRepository.java

package com.campus.marketplace.repository;

import com.campus.marketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}