package com.BMG_System_POC.demo.repository;

import com.BMG_System_POC.demo.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
}
