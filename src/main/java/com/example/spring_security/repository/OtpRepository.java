package com.example.spring_security.repository;

import com.example.spring_security.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Integer> {
    public Optional<Otp> findOtpByUsername(String username);
}
