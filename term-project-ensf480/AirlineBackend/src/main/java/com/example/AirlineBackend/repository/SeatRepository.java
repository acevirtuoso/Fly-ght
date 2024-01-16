package com.example.AirlineBackend.repository;

import com.example.AirlineBackend.model.Seat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
    
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightNumber(int flightNum);

    List<Seat> findBySeatBookedBy(String user);

    Seat findSeatById(Long id);
}