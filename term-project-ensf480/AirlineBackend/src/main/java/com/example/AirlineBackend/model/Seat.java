package com.example.AirlineBackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "SeatsBooked")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean seatAvailable;
    private Boolean insured;
    //private int price;
    //private int seatRow;
    //private char seatColumn;
    private String name;
    private int flightNumber;
    private String seatBookedBy;
    private String seatNumber;
    private String seatType;
    public Seat(Boolean seatAvailable, String seatBookedBy, String seatType, String seatNumber, int flightNumber, Boolean insured){
        this.seatAvailable = seatAvailable;
        this.seatBookedBy = seatBookedBy;
        this.seatType = seatType;
        this.seatNumber = seatNumber;
        this.flightNumber = flightNumber;
        this.insured = insured;
    }

    public Seat() {

    }

    public Boolean getSeatAvailable() {
        return seatAvailable;
    }

    public void setSeatAvailable(Boolean seatAvailable) {
        this.seatAvailable = seatAvailable;
    }

    public String getSeatBookedBy() {
        return seatBookedBy;
    }

    public void setSeatBookedBy(String seatBookedBy) {
        this.seatBookedBy = seatBookedBy;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public void setSeatID(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getInsured() {
        return insured;
    }

    public void setInsured(Boolean insured) {
        this.insured = insured;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }
}

