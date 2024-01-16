package com.example.AirlineBackend.model;

import lombok.Data;

import javax.persistence.*;

public class Ticket {
    private Long id;
    private int flightNumber;
    private String departure;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String seat;
    private String seatType;
    private Boolean insured;
    private Long flightID;


    public Ticket(Long id, int flightNumber, String departure, String destination, String departureTime, String arrivalTime, String seat, String seatType, Boolean insured, Long flightID) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seat = seat;
        this.seatType = seatType;
        this.insured = insured;
        this.flightID = flightID;
    }

    public Ticket() {

    }

    public Long getFlightID() {
        return flightID;
    }

    public void setFlightID(Long flightID) {
        this.flightID = flightID;
    }


    public void setId(Long id){
        this.id = id;
    }
    public void setFlightNumber(int flightNumber){
        this.flightNumber = flightNumber;
    }

    public void setDeparture(String departure){
        this.departure = departure;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public void setDepartureTime(String departureTime){
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime){
        this.arrivalTime = arrivalTime;
    }

    public void setSeat(String seat){
        this.seat = seat;
    }

    public void setSeatType(String seatType){
        this.seatType = seatType;
    }


    public Long getId(){
        return id;
    }

    public int getFlightNumber(){
        return flightNumber;
    }

    public String getDeparture(){
        return departure;
    }

    public String getDestination(){
        return destination;
    }

    public String getDepartureTime(){
        return departureTime;
    }

    public String getArrivalTime(){
        return arrivalTime;
    }

    public String getSeat(){
        return seat;
    }

    public String getSeatType(){
        return seatType;
    }

    public Boolean getInsured(){
        return insured;
    }

    public void setInsured(Boolean insured){
        this.insured = insured;
    }
}
