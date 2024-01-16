package com.example.AirlineBackend.data;

import java.util.ArrayList;
import java.util.List;
import com.example.AirlineBackend.model.Seat;

public class Payment {
    private Long flightID;
    private Long flightNumber; 
    private List<Seat> seats;
    private boolean hasTicketInsurance;
    private CreditCard creditCard;
    private String email;
    private String name;

    public Payment(Long flightID, Long flightNumber, List<Seat> seats, boolean hasTicketInsurance, CreditCard creditCard, String email) {
        this.flightID = flightID;
        this.flightNumber = flightNumber;
        this.seats = seats;
        this.hasTicketInsurance = hasTicketInsurance;
        this.creditCard = creditCard;
        this.email = email;
    }

    public Long getFlightID() {
        return flightID;
    }

    public void setFlightID(Long flightID) {
        this.flightID = flightID;
    }

    public Long getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Long flightNumber) {
        this.flightNumber = flightNumber;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setSingleSeat(Seat seat) {
        if (this.seats == null) {
            this.seats = new ArrayList<>();
        }
        this.seats.add(seat);
        System.out.println(seats);
    }


    public boolean isHasTicketInsurance() {
        return hasTicketInsurance;
    }

    public void setHasTicketInsurance(boolean hasTicketInsurance) {
        this.hasTicketInsurance = hasTicketInsurance;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}