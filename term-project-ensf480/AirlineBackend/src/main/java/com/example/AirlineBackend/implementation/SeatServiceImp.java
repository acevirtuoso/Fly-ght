package com.example.AirlineBackend.implementation;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.User;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.repository.FlightRepository;
import com.example.AirlineBackend.repository.SeatRepository;
import com.example.AirlineBackend.service.SeatService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImp implements SeatService {

    private SeatRepository seatRepository;
    private FlightRepository flightRepository;

    @Autowired
    public SeatServiceImp(SeatRepository seatRepository, FlightRepository flightRepository){
        super();
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    public Seat saveSeat(Seat seat){
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> getFlightsSeats(int flightNum) {
        return seatRepository.findByFlightNumber(flightNum);
    }

    @Override
    public List<Seat> getAllSeats() {
        return seatRepository.findByFlightNumber(3);
    }


    @Override
    public void deleteSeat(User user) {
        List<Seat> tmp = seatRepository.findBySeatBookedBy(user.getName());
    }

    @Override
    public Seat findSeat(Long id) {
        return seatRepository.findSeatById(id);
    }

    @Override
    public List<Seat> getUsersSeats(String user) {
        return null;
    }

    public void deleteUsersSeat(Long seatId){
        long temp = 2;
        seatRepository.deleteById(seatId);
        System.out.println("goes in here fine");
    }

    @Override
    public List<Ticket> getUsersTickets(String email) {
        List<Seat> seats = seatRepository.findBySeatBookedBy(email);
        List<Ticket> tickets = new ArrayList<>();
        for(int i = 0; i < seats.size(); i++){
            Flight temp = flightRepository.findByFlightNumber(seats.get(i).getFlightNumber());
            tickets.add(new Ticket(seats.get(i).getId(), seats.get(i).getFlightNumber(), temp.getDeparture(), temp.getDestination(), temp.getDepartureTime(), temp.getArrivalTime(), seats.get(i).getSeatNumber(), seats.get(i).getSeatType(), seats.get(i).getInsured(),temp.getId() ));
        }
        return tickets;
    }
}
