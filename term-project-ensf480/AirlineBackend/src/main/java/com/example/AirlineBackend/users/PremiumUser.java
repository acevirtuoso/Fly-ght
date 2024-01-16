package com.example.AirlineBackend.users;

import java.time.LocalDate;
import java.util.List;

import com.example.AirlineBackend.model.*;
import com.example.AirlineBackend.repository.PremiumUserRepository;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.service.AircraftService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremiumUser extends User {
    private static PremiumUser instance;
    private Ticket tickets[];
    private AircraftService aircraftService;
    private UserService userService;
    private FlightService flightService;
    private SeatService seatService;
    private PremiumUserRepository premiumUserRepository;

    @Autowired
    private PremiumUser(UserService userService, FlightService flightService, SeatService seatService, AircraftService aircraftService, PremiumUserRepository premiumUserRepository){
        super();
        this.aircraftService = aircraftService;
        this.userService = userService;
        this.seatService = seatService;
        this.flightService = flightService;
        this.premiumUserRepository = premiumUserRepository;
        tickets = new Ticket[20];
    }

    public static PremiumUser getInstance(UserService userService, FlightService flightService, SeatService seatService, AircraftService aircraftService, PremiumUserRepository premiumUserRepository){
        if(instance == null){
            instance = new PremiumUser(userService, flightService, seatService, aircraftService, premiumUserRepository);
        }
        return instance;
    }

    public boolean useVoucher(String email) {
        PremiumUserEntity premiumUser = premiumUserRepository.findByEmail(email);
        if (premiumUser.isMembership()) {
            LocalDate currentDate = LocalDate.now();
            LocalDate nextVoucherUsage = premiumUser.getLastVoucherUsage().plusYears(1);
    
            if (premiumUser.isVoucher() == true || currentDate.isAfter(nextVoucherUsage) || currentDate.isEqual(nextVoucherUsage)) {
                premiumUser.setVoucher(false);
                premiumUser.setLastVoucherUsage(LocalDate.now());
                premiumUserRepository.save(premiumUser);
                return true;
            } else {
                System.out.println("NO VOUCHER!");
                return false;
            }
        }  
        System.out.println("NO MEMBERSHIP!");
        return false; 
    }

    // THESE ARE WRONG I PROBABLY NEED TO MAKE GETTERS AND SETTERS FOR THIS
    // IN THE FRONTEND WHENEVER YOU LOAD THE PAGE IT WILL CALL THESE WHICH IS
    // NOT RIGHT. IT SHOULD BE EASY TO FIX.
    public boolean registerForMembership(String email) {
        PremiumUserEntity premiumUser = premiumUserRepository.findByEmail(email);
        premiumUser.setMembership(!premiumUser.isMembership());
        premiumUserRepository.save(premiumUser);
        return premiumUser.isMembership();
    }

    public boolean registerForMonthlyNews(String email) {
        PremiumUserEntity premiumUser = premiumUserRepository.findByEmail(email);
        if (premiumUser.isMembership()) {
            premiumUser.setMonthlyNews(!premiumUser.isMonthlyNews());
            premiumUserRepository.save(premiumUser);
        }
        return premiumUser.isMonthlyNews();
    }

    public boolean registerForCreditCard(String email) {
        PremiumUserEntity premiumUser = premiumUserRepository.findByEmail(email);
        if (premiumUser.isMembership()) {
            premiumUser.setCreditCard(!premiumUser.isCreditCard());
            premiumUserRepository.save(premiumUser);
        }
        return premiumUser.isCreditCard();
    }

    public List<Flight> browseFlights(){
        List<Aircraft> tempAircraft = aircraftService.getAllAircrafts();
        List<Flight> tempFlights = flightService.getRepo().findAll();
        for(int i = 0; i < tempFlights.size(); i++){
            for(int j = 0; j < tempAircraft.size(); j++){
                // if(tempAircraft.get(j).getAircraftID() == tempFlights.get(i).getAircraftID()){}
                // Aircraft temp = tempAircraft.get(j);
                // tempFlights.get(i).setAircraft(temp);
                if(tempAircraft.get(j).getAircraftID().equals(tempFlights.get(i).getAircraftID())){
                    System.out.println(tempAircraft.get(j).getAircraftID());
                    System.out.println(tempFlights.get(i).getAircraftID());
                    Aircraft temp = tempAircraft.get(j);
                    Flight temp2 = tempFlights.get(i);
                    System.out.println("Aircraft ID is " + temp.getAircraftID());
                    System.out.println("Flight wanting to insert into is " + temp2.getAircraftID());
                    temp2.setAircraft(new Aircraft(temp.getAircraftID(), temp.getLastBusinessRow(), temp.getLastComfortRow(), temp.getTotalBusinessColumns(), temp.getTotalRegularColumns(), temp.getRows(), temp.getColumns()));
                    temp2.setSeatMap(new SeatMap(tempFlights.get(i).getFlightNumber()));
                    List<Seat> waka = seatService.getFlightsSeats(tempFlights.get(i).getFlightNumber());
                    System.out.println(waka.size());
                    temp2.getSeatMap().setBookedSeats(seatService.getFlightsSeats(tempFlights.get(i).getFlightNumber()));
                }
            }
        }
        return tempFlights;
    }

    public Seat[] browseSeats(Flight flight){
        Flight databaseFlight = flightService.getFlightId(flight.getId());
        return databaseFlight.getSeatMap().getSeats();
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }
}

