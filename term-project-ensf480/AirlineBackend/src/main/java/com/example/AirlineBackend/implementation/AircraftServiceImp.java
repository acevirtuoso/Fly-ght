package com.example.AirlineBackend.implementation;

import com.example.AirlineBackend.model.Aircraft;
import com.example.AirlineBackend.repository.AircraftRepository;
import com.example.AirlineBackend.service.AircraftService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AircraftServiceImp implements AircraftService {

    private AircraftRepository aircraftRepository;

    @Autowired
    public AircraftServiceImp(AircraftRepository aircraftRepository){
        super();
        this.aircraftRepository = aircraftRepository;
    }
    @Override
    public Aircraft saveAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    @Override
    public void deleteAllAircrafts() {
        aircraftRepository.deleteAll();
    }

    @Override
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    @Override
    public void removeAircraft(Aircraft aircraft) {
        aircraftRepository.delete(aircraft);
    }

    public Aircraft getAircraftbyID(String aircraftID){
        return aircraftRepository.getAircraftByAircraftID(aircraftID);
    }
}
