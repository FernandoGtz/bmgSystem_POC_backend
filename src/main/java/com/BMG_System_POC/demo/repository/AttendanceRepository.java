package com.BMG_System_POC.demo.repository;

import com.BMG_System_POC.demo.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
}
