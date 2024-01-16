package com.example.AirlineBackend.users;

import com.example.AirlineBackend.model.*;
import com.example.AirlineBackend.repository.CrewRepository;
import com.example.AirlineBackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FlightCrew extends User implements CrewService {
    private static FlightCrew instance;
    private SeatService seatService;
    private UserService userService;
    private AircraftService aircraftService;
    private FlightService flightService;
    private CrewRepository crewRepository;
    private ArrayList<Flight> registeredFlights[];
    @Autowired
    private FlightCrew(UserService userService, SeatService seatService, CrewRepository crewRepository, FlightService flightService, AircraftService aircraftService) {
        super();
        this.aircraftService = aircraftService;
        this.userService = userService;
        this.crewRepository = crewRepository;
        this.flightService = flightService;
        registeredFlights = new ArrayList[30];
        this.seatService = seatService;
    }

    public static FlightCrew getInstance(UserService userService, SeatService seatService, CrewRepository crewRepository, FlightService flightService, AircraftService aircraftService){
        if(instance == null){
            instance = new FlightCrew(userService, seatService, crewRepository, flightService, aircraftService);
        }
        return instance;
    }
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ArrayList<Flight>[] getRegisteredFlights() {
        return registeredFlights;
    }

    public void setRegisteredFlights(ArrayList<Flight>[] registeredFlights) {
        this.registeredFlights = registeredFlights;
    }

    public List<Seat> browsePassengers(int flightNumber){
        List<Seat> registered = seatService.getFlightsSeats(flightNumber);
        return registered;
    }

    public void registerForFlight(ArrayList<Flight> flight){
        for(int i = 0; i < 30; i++){
            if(registeredFlights[i].isEmpty()){
                registeredFlights[i] = flight;
            }
        }
    }

    @Override
    public Crew saveCrew(Crew crew) {
        return crewRepository.save(crew);
    }

    @Override
    public void removeCrew(Crew crew) {
        crewRepository.delete(crew);
    }
    
    @Override
    public List<Crew> getAllCrew() {
        return crewRepository.findAll();
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Aircraft> tempAircraft = aircraftService.getAllAircrafts();
        List<Flight> tempFlights = flightService.getRepo().findAll();
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
}
