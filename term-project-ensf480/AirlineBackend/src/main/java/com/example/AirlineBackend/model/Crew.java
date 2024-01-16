package com.example.AirlineBackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CrewMembers")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int flightNumber;
    private String name;

    public Crew(int flightNumber, String name){
        this.flightNumber = flightNumber;
        this.name = name;
    }
    
    public Crew() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }
}
