package com.example.AirlineBackend.service;

import com.example.AirlineBackend.model.Crew;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.users.FlightCrew;

import java.util.List;

public interface CrewService {

    Crew saveCrew(Crew crew);

    void removeCrew(Crew crew);

    List<Crew> getAllCrew();

    List<Flight> getAllFlights();
}
