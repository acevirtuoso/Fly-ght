package com.example.AirlineBackend.service;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.repository.FlightRepository;

import java.util.List;

public interface FlightService {

    Flight saveFlight(Flight flight);

    List<Flight> getAllFlights();

    Flight getFlightId(Long flightId);

    Flight getFlight(Flight flight);

    void deleteAllFlights();

    void deleteFlightId(Long flightId);

    void deleteFlight(Flight flight);

    FlightRepository getRepo();

    void removeAircraftId(String id);
}
