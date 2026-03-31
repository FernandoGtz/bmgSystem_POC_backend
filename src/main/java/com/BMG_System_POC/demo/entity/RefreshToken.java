package com.BMG_System_POC.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true,  nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expires;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Instant getExpires() {
        return expires;
    }
    public void setExpires(Instant expires) {
        this.expires = expires;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
