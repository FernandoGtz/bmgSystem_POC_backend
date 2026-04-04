package com.BMG_System_POC.demo.repository;

import com.BMG_System_POC.demo.entity.RefreshToken;
import com.BMG_System_POC.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);

    @Modifying
    void deleteByExpiresBefore(Instant timestamp);
}
