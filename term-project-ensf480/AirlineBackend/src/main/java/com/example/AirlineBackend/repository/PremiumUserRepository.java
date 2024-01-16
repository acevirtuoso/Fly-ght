package com.example.AirlineBackend.repository;

import com.example.AirlineBackend.model.PremiumUserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremiumUserRepository extends JpaRepository<PremiumUserEntity, Long> {
    
    PremiumUserEntity findByEmail(String email);

}
