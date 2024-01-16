package com.example.AirlineBackend.users;

import com.example.AirlineBackend.controller.FlightController;
import com.example.AirlineBackend.model.*;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.repository.FlightRepository;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUser extends User {
    private static DefaultUser instance;
    private Ticket tickets[];
    private AircraftService aircraftService;
    private UserService userService;
    private FlightService flightService;

    private FlightRepository flightRepository;
    private SeatService seatService;
    private FlightController flightController;
    @Autowired
    private DefaultUser(FlightService flightService, UserService userService, SeatService seatService, AircraftService aircraftService, FlightRepository flightRepository, FlightController flightController){
        super();
        this.aircraftService = aircraftService;
        this.flightService = flightService;
        this.userService = userService;
        this.seatService = seatService;
        this.flightRepository = flightRepository;
        this.flightController = flightController;
    }

    public static DefaultUser getInstance(FlightService flightService, UserService userService, SeatService seatService, AircraftService aircraftService, FlightRepository flightRepository, FlightController flightController){
        if(instance == null){
            instance = new DefaultUser(flightService, userService, seatService, aircraftService, flightRepository,flightController);
        }
        return instance;
    }

    public List<Flight> browseFlights(){
        List<Aircraft> tempAircraft = aircraftService.getAllAircrafts();
        List<Flight> tempFlights = flightRepository.findAll();
        for(int i = 0; i < tempFlights.size(); i++){
            for(int j = 0; j < tempAircraft.size(); j++){
                if(tempAircraft.get(j).getAircraftID().equals(tempFlights.get(i).getAircraftID())){
                    System.out.println(tempAircraft.get(j).getAircraftID());
                    System.out.println(tempFlights.get(i).getAircraftID());
                    Aircraft temp = tempAircraft.get(j);
                    Flight temp2 = tempFlights.get(i);
                    System.out.println("Aircraft ID is " + temp.getAircraftID());
                    System.out.println("Flight wanting to insert into is " + temp2.getAircraftID());
                    temp2.setAircraft(new Aircraft(temp.getAircraftID(), temp.getLastBusinessRow(), temp.getLastComfortRow(), temp.getTotalBusinessColumns(), temp.getTotalRegularColumns(), temp.getRows(), temp.getColumns()));
                    temp2.setSeatMap(new SeatMap(tempFlights.get(i).getFlightNumber()));
                    List<Seat> waka = seatService.getFlightsSeats(tempFlights.get(i).getFlightNumber());
                    System.out.println(waka.size());
                    temp2.getSeatMap().setBookedSeats(seatService.getFlightsSeats(tempFlights.get(i).getFlightNumber()));
                }
            }
        }
        return tempFlights;
    }

    public Seat[] browseSeats(Flight flight){
        Flight databaseFlight = flightService.getFlightId(flight.getId());
        return databaseFlight.getSeatMap().getSeats();
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }
}

