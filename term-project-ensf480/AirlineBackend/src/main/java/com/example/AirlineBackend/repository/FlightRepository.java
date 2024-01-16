package com.example.AirlineBackend.repository;

import com.example.AirlineBackend.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findByFlightNumber(int flightNum);

    void deleteFlightByFlightNumber(int flightNum);

}
