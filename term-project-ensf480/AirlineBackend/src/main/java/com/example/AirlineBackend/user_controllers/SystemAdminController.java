package com.example.AirlineBackend.user_controllers;

import com.example.AirlineBackend.model.*;
import com.example.AirlineBackend.service.*;
import com.example.AirlineBackend.users.SystemAdmin;

import org.hibernate.annotations.SourceType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemAdmin")
@CrossOrigin(origins = "*")
public class SystemAdminController {
    private SystemAdmin systemAdmin;
    private UserService userService;
    private FlightService flightService;
    private SeatService seatService;
    private AircraftService aircraftService;
    private CrewService crewService;
    private SeatMapService seatMapService;
    
    public SystemAdminController(SystemAdmin systemAdmin, UserService userService, FlightService flightService, AircraftService aircraftService, SeatService seatService, SeatMapService seatMapService, CrewService crewService){
        super();
        this.systemAdmin = systemAdmin;
        this.userService = userService;
        this.flightService = flightService;
        this.seatService = seatService;
        this.aircraftService = aircraftService;
        this.crewService = crewService;
        this.seatMapService = seatMapService;
    }

    @GetMapping("/browseFlights")
    public List<Flight> browseFlights(){
        System.out.println("asdasdasdasdsad");
        System.out.println("asdasdasdasdsad");
        System.out.println("asdasdasdasdsad");
        System.out.println("asdasdasdasdsad");
        System.out.println("asdasdasdasdsad");
        return systemAdmin.browseFlights();
    }

    @GetMapping("/printUsers")
    public List<User> printAllUsers(){
        return systemAdmin.printAllUsers();
    }

    @PostMapping(value = "/addFlight", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addFlight(@RequestBody Flight flight) {
        System.out.println(flight);
        System.out.println(flight);
        System.out.println(flight);
        System.out.println(flight);
        systemAdmin.addFlight(flight);
        if(flight.getAircraftID() != null){
            Aircraft tmp2 = new Aircraft(flight.getAircraftID(), 2, 5, 2, 2, 3, 6);
            tmp2.setFlight(flight);
            systemAdmin.saveAircraft(tmp2);
        }
        SeatMap tmp = new SeatMap(flight.getFlightNumber());
        tmp.setFlight(flight);
        systemAdmin.saveSeats(tmp);
        return "Flight has been added";
    }

    @DeleteMapping("/removeFlight")
    public String removeFlight(@RequestBody Flight flight){
        systemAdmin.removeFlight(flight);
        return "Deleted flight " + flight.getFlightNumber();
    }

    @GetMapping("/browseCrew/{flightNumber}")
    public List<Crew> browseCrew(@PathVariable int flightNumber){
        return systemAdmin.browseFlightCrew(flightNumber);
    }
    @PostMapping("/addCrew")
    public String addCrew(@RequestBody Crew crew){ //@PathVariable String email, @PathVariable String password) {
       return systemAdmin.addCrew(crew);//, email, password);
    }

    @DeleteMapping("/removeCrew")
    public String removeCrew(@RequestBody Crew crew){
        return systemAdmin.RemoveCrew(crew);
    }

    @GetMapping("/browseAircafts")
    public List<Aircraft> browseAircrafts(){
        return systemAdmin.browseAircrafts();
    }

    @PostMapping("/addAircraft")
    public String addAircraft(@RequestBody Aircraft aircraft){
        return systemAdmin.addAircraft(aircraft);
    }

    @DeleteMapping("/removeAircraft")
    public String removeAircraft(@RequestBody Aircraft aircraft){
        return systemAdmin.removeAircraft(aircraft);
    }

    @PutMapping("/assignAircraft/{aircraftID}")
    public String assignAircraft(@RequestBody Flight flight, @PathVariable String aircraftID){
       return systemAdmin.assignAircraft(flight, aircraftID);
    }

    @PutMapping("/re-assignAircraft/{flightNumber}")
    public String reAssignAircraft(@PathVariable int flightNumber){
        return systemAdmin.takeOutAircraft(flightNumber);
    }

    @PutMapping("/removeDest/{flightNumber}")
    public String removeDest(@PathVariable int flightNumber){
        return systemAdmin.removeDest(flightNumber);
    }

    @PutMapping("/addDest/{flightNumber}")
    public String addDest(@PathVariable int flightNumber, @RequestBody String dest){
        return systemAdmin.addDest(flightNumber, dest);
    }
}
