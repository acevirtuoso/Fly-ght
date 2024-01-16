package com.example.AirlineBackend.repository;

import com.example.AirlineBackend.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Aircraft getAircraftByAircraftID(String aircraftID);
}
