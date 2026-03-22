package com.BMG_System_POC.demo.repository;

import com.BMG_System_POC.demo.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}
