package com.example.AirlineBackend.service;

import java.util.List;

import com.example.AirlineBackend.model.Aircraft;

public interface AircraftService {
    Aircraft saveAircraft(Aircraft aircraft);

    void deleteAllAircrafts();

    List<Aircraft> getAllAircrafts();

    void removeAircraft(Aircraft aircraft);

}
