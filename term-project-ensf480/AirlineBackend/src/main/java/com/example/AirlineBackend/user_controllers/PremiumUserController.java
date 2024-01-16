package com.example.AirlineBackend.user_controllers;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.PremiumUserEntity;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.repository.PremiumUserRepository;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import com.example.AirlineBackend.users.PremiumUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/premiumUser")
@CrossOrigin(origins = "http://localhost:3000")
public class PremiumUserController {
    private PremiumUser premiumUser;
    private AircraftService aircraftService;
    private UserService userService;
    private FlightService flightService;
    private SeatService seatService;
    private PremiumUserRepository premiumUserRepository;
    public PremiumUserController(UserService userService, FlightService flightService, SeatService seatService, AircraftService aircraftService, PremiumUserRepository premiumUserRepository){
        super();
        premiumUser = PremiumUser.getInstance(userService, flightService, seatService, aircraftService, premiumUserRepository);
        this.aircraftService = aircraftService;
        this.userService = userService;
        this.seatService = seatService;
        this.flightService = flightService;
        this.premiumUserRepository = premiumUserRepository;
    }
    @GetMapping("/browseFlights")
    public List<Flight> browseFlights(){
        return premiumUser.browseFlights();
    }

    @GetMapping("/browseSeats")
    public Seat[] browseSeats(Flight flight){
        return premiumUser.browseSeats(flight);
    }

    @GetMapping("/getTickets")
    public Ticket[] getTickets(){
        return premiumUser.getTickets();
    }

    @GetMapping("/getVoucher")
    public boolean getVoucher(String email){
        return premiumUser.useVoucher(email);
    }

    @GetMapping("/registerForMonthly")
    public boolean registerForMonthly(String email){
        return premiumUser.registerForMonthlyNews(email);
    }

    @GetMapping("/registerForMembership")
    public boolean registerForMembership(String email){
        return premiumUser.registerForMembership(email);
    }

    @GetMapping("/registerForCreditCard")
    public boolean registerForCreditCard(String email){
        return premiumUser.registerForCreditCard(email);
    }


}
