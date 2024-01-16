package com.example.AirlineBackend.user_controllers;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.User;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import com.example.AirlineBackend.users.AirlineAgent;
import org.springframework.web.bind.annotation.*;
import com.example.AirlineBackend.model.Ticket;

import java.util.List;

@RestController
@RequestMapping("/airlineAgent")
@CrossOrigin(origins = "http://localhost:3000")
public class AirlineAgentController {
    private AirlineAgent airlineAgent;
    private FlightService flightService;
    private UserService userService;
    private SeatService seatService;
    private AircraftService aircraftService;
    public AirlineAgentController(FlightService flightService, UserService userService, SeatService seatService, AircraftService aircraftService){
        super();
        airlineAgent = AirlineAgent.getInstance(flightService, userService, seatService, aircraftService);
        this.aircraftService = aircraftService;
        this.flightService = flightService;
        this.userService = userService;
        this.seatService = seatService;
    }
    @GetMapping("/browseFlights")
    public List<Flight> browseFlights(){
        return airlineAgent.browseFlights();
    }

    @GetMapping("/browseSeats")
    public Seat[] browseSeats(Flight flight){
        return airlineAgent.browseSeats(flight);
    }

    @GetMapping("/getTickets")
    public Ticket[] getTickets(){
        return airlineAgent.getTickets();
    }

    @GetMapping("/browsePassengers/{flightNumber}")
    public List<Seat> browsePassengers(@PathVariable int flightNumber){
        return airlineAgent.browsePassengers(flightNumber);
    }
}
