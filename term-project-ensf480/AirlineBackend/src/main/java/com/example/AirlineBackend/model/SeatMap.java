package com.example.AirlineBackend.model;

import javax.persistence.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Entity
@Table(name = "SeatMaps")
public class SeatMap implements Observer {
    @Transient
    private Seat seats[];
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "flightNumber", referencedColumnName = "flightNumber")
    private Flight flight;

    public SeatMap(int flightNumber) {
        seats = new Seat[90];
        for (int i = 0; i < 15; i++) {
            for (char j = 'a'; j <= 'f'; j++) {
                String result = i + String.valueOf(j);
                String seatType = getSeatType(i * 6 + (j - 'a' + 1));
                Seat tmp = new Seat(true, "none", seatType, result, flightNumber, false);
                long ha = 1;
                tmp.setSeatID(ha);
                seats[i * 6 + (j - 'a')] = tmp;
                //seat.add(new Seat(true, "none", seatType, result, flightNumber, false));
            }
        }
    }

    public SeatMap() {

    }

    private String getSeatType(int seatNumber) {
        if (seatNumber <= 8) {
            return "Business";
        } else if (seatNumber <= 24) {
            return "Comfort";
        } else {
            return "Regular";
        }
    }

    public void setBookedSeats(List<Seat> bookedSeats){
        for(int i = 0; i < bookedSeats.size();  i++){
            String seatNumber = bookedSeats.get(i).getSeatNumber();
            System.out.println(seatNumber);
            String rowNumber = seatNumber.substring(0, seatNumber.length() - 1);
            int row = Integer.parseInt(rowNumber);
            String seatType = seatNumber.substring(seatNumber.length() - 1);
            char letter = seatType.charAt(0);
            int column = (int) letter - 65;
            int spot = (row - 1) * 6 + column;
            seats[spot] = bookedSeats.get(i); 
        }
    }

    public Seat[] getSeats(){
        return seats;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFlight(Flight flight){
        this.flight = flight;
    }

    public void setSeats(Seat[] seats) {
        this.seats = seats;
    }

    public Flight getFlight() {
        return flight;
    }
}
