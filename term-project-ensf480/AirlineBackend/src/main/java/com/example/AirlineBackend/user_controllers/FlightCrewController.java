package com.example.AirlineBackend.user_controllers;

import com.example.AirlineBackend.model.Crew;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.User;
import com.example.AirlineBackend.repository.CrewRepository;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import com.example.AirlineBackend.users.FlightCrew;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flightCrew")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightCrewController {
    private FlightCrew flightCrew;
    private SeatService seatService;
    private UserService userService;
    private AircraftService aircraftService;
    private FlightService flightService;
    private CrewRepository crewRepository;
    public FlightCrewController(UserService userService, SeatService seatService, CrewRepository crewRepository, FlightService flightService, AircraftService aircraftService){
        super();
        flightCrew = FlightCrew.getInstance(userService, seatService, crewRepository, flightService, aircraftService);
        this.aircraftService = aircraftService;
        this.userService = userService;
        this.crewRepository = crewRepository;
        this.flightService = flightService;
        this.seatService = seatService;
    }

    @GetMapping("/browsePassengers/{flightNumber}")
    public List<Seat> browsePassengers(@PathVariable int flightNumber){
       return flightCrew.browsePassengers(flightNumber);
    }

    @PostMapping("/addFlightCrew")
    public void addCrew(Crew crew){
        flightCrew.saveCrew(crew);
    }
    @DeleteMapping("/removeCrew")
    public void removeCrew(@RequestBody Crew crew){
        flightCrew.removeCrew(crew);
    }

    @GetMapping("/getAllCrew")
    public List<Crew> getAllCrew(){
        return flightCrew.getAllCrew();
    }

    @GetMapping("/browseFlights")
    public List<Flight> getAllFlights(){
        return flightCrew.getAllFlights();
    }

}
