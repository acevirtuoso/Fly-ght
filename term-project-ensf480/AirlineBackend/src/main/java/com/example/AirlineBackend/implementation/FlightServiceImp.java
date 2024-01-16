package com.example.AirlineBackend.implementation;

import com.example.AirlineBackend.model.Aircraft;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.SeatMap;
import com.example.AirlineBackend.repository.FlightRepository;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FlightServiceImp implements FlightService {

    private FlightRepository flightRepository;
    private AircraftService aircraftService;
    private SeatService seatService;

    @Autowired
    public FlightServiceImp(FlightRepository flightRepository, AircraftService aircraftService, SeatService seatService){
        super();
        this.flightRepository = flightRepository;
        this.aircraftService = aircraftService;
        this.seatService = seatService;
    }

    @Override
    public Flight saveFlight(Flight flight){
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Aircraft> tempAircraft = aircraftService.getAllAircrafts();
        List<Flight> tempFlights = flightRepository.findAll();
        for(int i = 0; i < tempFlights.size(); i++){    
            for(int j = 0; j < tempAircraft.size(); j++){
                System.out.println("goes in the nested loop");
                System.out.println("goes in the nested loop");
                System.out.println("goes in the nested loop");
                System.out.println("goes in the nested loop");
                System.out.println("goes in the nested loop");
                
                // if(tempAircraft.get(j).getAircraftID() == tempFlights.get(i).getAircraftID()){}
                // Aircraft temp = tempAircraft.get(j);
                // tempFlights.get(i).setAircraft(temp);
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
                    System.out.println("The seat size is " + waka.size());
                    temp2.getSeatMap().setBookedSeats(seatService.getFlightsSeats(tempFlights.get(i).getFlightNumber()));
                }
            }
        }
        return tempFlights;
    }

    @Override
    public Flight getFlightId(Long flightId) {
        return flightRepository.getReferenceById(flightId);
    }

    @Override
    public Flight getFlight(Flight flight) {
        return null;
    }

    @Override
    public void deleteAllFlights() {
        flightRepository.deleteAll();
    }
    @Override
    public void deleteFlightId(Long flightId) {
        flightRepository.deleteById(flightId);
    }

    @Override
    public void deleteFlight(Flight flight) {
        flightRepository.deleteById(flight.getId());
    }

    @Override
    public FlightRepository getRepo() {
        return flightRepository;
    }

    @Override
    @Transactional
    public void removeAircraftId(String id) {
        List<Flight> flights = getAllFlights();
        for (Flight tmp : flights) {
            if (Objects.equals(tmp.getAircraftID(), id)) {
                tmp.setAircraftID(null);
            }
        }
    }
}
