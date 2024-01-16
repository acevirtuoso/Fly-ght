package com.example.AirlineBackend.service;

import com.example.AirlineBackend.model.SeatMap;

import java.util.List;

public interface SeatMapService {
    SeatMap saveSeats(SeatMap seatMap);
    void deleteAllSeatMaps();
    SeatMap getSeatMap();
    void removeSeatMap(SeatMap seatMap);
    List<SeatMap> getAllSeatMaps();

}
