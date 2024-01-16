package com.example.AirlineBackend.implementation;

import com.example.AirlineBackend.model.SeatMap;
import com.example.AirlineBackend.repository.SeatMapRepository;
import com.example.AirlineBackend.service.SeatMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatMapServiceImp implements SeatMapService {

    private SeatMapRepository seatMapRepository;

    @Autowired
    public SeatMapServiceImp(SeatMapRepository seatMapRepository){
        super();
        this.seatMapRepository = seatMapRepository;
    }
    @Override
    public SeatMap saveSeats(SeatMap seatMap) {
        return seatMapRepository.save(seatMap);
    }

    @Override
    public void deleteAllSeatMaps() {
        seatMapRepository.deleteAll();
    }

    @Override
    public SeatMap getSeatMap() {
        long temp = 1;
        return seatMapRepository.getReferenceById(temp);
    }

    @Override
    public void removeSeatMap(SeatMap seatMap) {
        seatMapRepository.delete(seatMap);
    }

    @Override
    public List<SeatMap> getAllSeatMaps() {
        return seatMapRepository.findAll();
    }
}
