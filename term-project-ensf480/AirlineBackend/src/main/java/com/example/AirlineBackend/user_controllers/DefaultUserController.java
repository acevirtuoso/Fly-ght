package com.example.AirlineBackend.user_controllers;

import com.example.AirlineBackend.controller.FlightController;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.repository.FlightRepository;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import com.example.AirlineBackend.users.DefaultUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/defaultUser")
@CrossOrigin(origins = "http://localhost:3000")
public class DefaultUserController {
    private DefaultUser defaultUser;
    private AircraftService aircraftService;
    private UserService userService;
    private FlightService flightService;
    private FlightRepository flightRepository;
    private SeatService seatService;
    private FlightController flightController;
    public DefaultUserController(FlightService flightService, UserService userService, SeatService seatService, AircraftService aircraftService, FlightRepository flightRepository, FlightController flightController){
        super();
        defaultUser = DefaultUser.getInstance(flightService, userService, seatService, aircraftService, flightRepository, flightController);
        this.aircraftService = aircraftService;
        this.flightService = flightService;
        this.userService = userService;
        this.seatService = seatService;
        this.flightRepository = flightRepository;
        this.flightController = flightController;
    }
    @GetMapping("/browseFlights")
    public List<Flight> browseFlights(){
        return defaultUser.browseFlights();
    }

    @GetMapping("/browseSeats")
    public Seat[] browseSeats(Flight flight){
        return defaultUser.browseSeats(flight);
    }

    @GetMapping("/getTickets")
    public Ticket[] getTickets(){
        return defaultUser.getTickets();
    }

}
