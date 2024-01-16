package com.example.AirlineBackend.repository;

import com.example.AirlineBackend.model.Crew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewRepository extends JpaRepository<Crew, Long> {

    Crew findByName(String name);
    Crew findByFlightNumber(int flightNumber);
    Crew findAllByFlightNumber(int flightNumber);
}
