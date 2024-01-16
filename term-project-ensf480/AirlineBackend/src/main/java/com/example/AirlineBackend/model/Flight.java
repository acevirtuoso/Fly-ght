package com.example.AirlineBackend.model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Flights", uniqueConstraints = @UniqueConstraint(columnNames = "flightNumber"))
public class Flight implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private Aircraft aircraft;
    private String aircraftID;
    private int flightNumber;
    private String departure;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int duration;

    @Transient
    @OneToOne(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private SeatMap seatMap;

    public Flight(int flightNumber, String departure, String destination, String departureTime, String arrivalTime, int duration, String aircraftID) {
        this.flightNumber = flightNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.aircraftID = aircraftID;
        this.seatMap = new SeatMap(flightNumber);
    }

    public Flight() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(String aircraftID) {
        this.aircraftID = aircraftID;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public SeatMap getSeatMap() {
        return seatMap;
    }

    public void setSeatMap(SeatMap seatMap) {
        this.seatMap = seatMap;
    }
}
