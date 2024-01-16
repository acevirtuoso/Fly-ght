package com.example.AirlineBackend.service;

import java.util.List;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.model.User;

public interface SeatService {
       List<Seat> getFlightsSeats(int flightNum);

       Seat saveSeat(Seat seat);

       List<Seat> getAllSeats();

       List<Seat> getUsersSeats(String user);

       void deleteSeat(User user);

       public Seat findSeat(Long id);

       public void deleteUsersSeat(Long seatId);

       public List<Ticket> getUsersTickets(String email);
}
