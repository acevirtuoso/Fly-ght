package com.example.AirlineBackend.controller;

import com.example.AirlineBackend.model.Aircraft;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.SeatMap;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatMapService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flight")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightController {
    private FlightService flightService;
    private SeatService seatService;
    private SeatMapService seatMapService;
    private AircraftService aircraftService;

    public FlightController(FlightService flightService, SeatMapService seatMapService, AircraftService aircraftService, SeatService seatService){
        super();
        this.flightService = flightService;
        this.seatMapService = seatMapService;
        this.aircraftService = aircraftService;
        this.seatService = seatService;
    }

    @PostMapping(value = "/addFlight", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String add(@RequestBody Flight flight){
        flightService.saveFlight(flight);
        if(flight.getAircraftID() != null){
            Aircraft tmp2 = new Aircraft(flight.getAircraftID(), 2, 5, 2, 2, 3, 6);
            tmp2.setFlight(flight);
            aircraftService.saveAircraft(tmp2);
        }
        SeatMap tmp = new SeatMap(flight.getFlightNumber());
        tmp.setFlight(flight);
        seatMapService.saveSeats(tmp);
        return "Flight added";
    }

    @GetMapping("/getAllFlights")
    public List<Flight> getAllFlights(){
        return flightService.getAllFlights();
    }

    @GetMapping("/getFlightId")
    public Flight getFlightId(Long flightId){
        return flightService.getFlightId(flightId);
    }

    @DeleteMapping("/deleteAllFlights")
    public String deleteAllFlights(){
        seatMapService.deleteAllSeatMaps();
        aircraftService.deleteAllAircrafts();
        flightService.deleteAllFlights();
        return "All flights have been deleted";
    }

    @DeleteMapping("/deleteFlightId")
    public void deleteFlightId(Long flightId){
        flightService.deleteFlightId(flightId);
    }

    @DeleteMapping("/deleteFlight")
    public void deleteFlight(Flight flight) {
        flightService.deleteFlight(flight);
    }

    @GetMapping("/getSeatMap")
    public SeatMap getSeatMap(){
        return seatMapService.getSeatMap();
    }

    @GetMapping("/getSingleSeat")  //change to PostMapping later
    public List<Seat> getFlightsSeats() {
        return seatService.getAllSeats();
    }

    @PostMapping(value = "/seatBooked", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String seatBooked(@RequestBody Seat seat){
        System.out.println("Reaches");
        seatService.saveSeat(seat);
        System.out.println(seat.getFlightNumber());
        System.out.println(seat.getSeatBookedBy());
        System.out.println(seat.getSeatNumber());
        System.out.println(seat.getSeatType());
        System.out.println(seat.getId());
        return "Seat has been booked";
    }

    @PutMapping("/removeACId/{id}")
    public void removeId(@PathVariable String id){
        flightService.removeAircraftId(id);
    }
}
