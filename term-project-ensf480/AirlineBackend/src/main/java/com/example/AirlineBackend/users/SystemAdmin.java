package com.example.AirlineBackend.users;

import com.example.AirlineBackend.model.*;
import com.example.AirlineBackend.service.*;
import com.example.AirlineBackend.user_controllers.FlightCrewController;
import com.example.AirlineBackend.users.FlightCrew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SystemAdmin extends User {
    private UserService userService;
    private FlightService flightService;
    private SeatService seatService;
    private AircraftService aircraftService;
    private CrewService crewService;
    private SeatMapService seatMapService;

    @Autowired
    public SystemAdmin(UserService userService, FlightService flightService, AircraftService aircraftService, SeatService seatService, SeatMapService seatMapService, CrewService crewService) {
        super();
        this.crewService = crewService;
        this.seatMapService = seatMapService;
        this.seatService = seatService;
        this.aircraftService = aircraftService;
        this.flightService = flightService;
        this.userService = userService;
    }

    public List<Flight> browseFlights() {
        List<Aircraft> tempAircraft = aircraftService.getAllAircrafts();
        List<Flight> tempFlights = flightService.getRepo().findAll();
        for (int i = 0; i < tempFlights.size(); i++) {
            for (int j = 0; j < tempAircraft.size(); j++) {
                if (tempAircraft.get(j).getAircraftID().equals(tempFlights.get(i).getAircraftID())) {
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

    public List<User> printAllUsers() {
        return userService.getAllUsers();
    }

    public List<Crew> browseFlightCrew(int flightNumber) {
        List<Crew> users = crewService.getAllCrew();
        ArrayList<Crew> crew = new ArrayList<>();
        for (Crew user : users) {
            if (user.getFlightNumber() == flightNumber) {
                crew.add(user);
            }
        }
        return crew;
    }

    public void addFlight(Flight flight) {
        flightService.saveFlight(flight);
    }

    public void removeFlight(Flight flight) {

        List<Flight> tmp = flightService.getAllFlights();
        List<SeatMap> seats = seatMapService.getAllSeatMaps();
        for (SeatMap seatMap : seats) {
            if (seatMap.getFlight().getFlightNumber() == flight.getFlightNumber()) {
                seatMapService.removeSeatMap(seatMap);
                break;
            }
        }
        for (Flight tmp2 : tmp) {
            if (tmp2.getFlightNumber() == flight.getFlightNumber()) {
                flightService.deleteFlight(tmp2);
                break;
            }
        }
    }


    


    

    public void saveSeats(SeatMap tmp) {
        seatMapService.saveSeats(tmp);
    }

    public void saveAircraft(Aircraft tmp2) {
        aircraftService.saveAircraft(tmp2);
    }

    @Transactional
    public String addCrew(Crew crew){//, String email, String password) {
        crewService.saveCrew(crew);
        User tmp = new User(crew.getName(),crew.getName() + "@gmail.com", crew.getName() + "123", "flight-crew");
        tmp.setBalance(0);
        userService.saveUser(tmp);
        return "Added crew with " + crew.getName() + ", " + crew.getFlightNumber();
    }

    public String RemoveCrew(Crew crew) {
        List<Crew> crewMembers = crewService.getAllCrew();
        List<User> users = userService.getAllUsers();
        for(User tmp : users){
            if(Objects.equals(crew.getName(), tmp.getName())){
                userService.deleteUser(tmp.getId());
            }
        }
        for (Crew tmp : crewMembers) {
            if (tmp.getFlightNumber() == crew.getFlightNumber() && Objects.equals(tmp.getName(), crew.getName())) {
                crewService.removeCrew(tmp);
                break;
            }
        }
        return "Removed crew";
    }

    public List<Aircraft> browseAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    public String addAircraft(Aircraft aircraft) {
        aircraftService.saveAircraft(aircraft);
        return "Aircraft added";
    }

    @Transactional
    public String removeAircraft(Aircraft aircraft) {
        List<Aircraft> crafts = aircraftService.getAllAircrafts();
        List<Flight> flights = flightService.getAllFlights();
        for(Flight tmp : flights){
            if(Objects.equals(tmp.getAircraftID(), aircraft.getAircraftID())){
                flightService.removeAircraftId(aircraft.getAircraftID());
            }
        }

        for (Aircraft tmp : crafts) {
            if (Objects.equals(tmp.getAircraftID(), aircraft.getAircraftID())) {
                aircraftService.removeAircraft(tmp);
                break;
            }
        }
        return "Aircraft " + aircraft.getAircraftID() + " deleted";
    }

    @Transactional
    public String assignAircraft(Flight flight, String aircraftID) {
        List<Aircraft> tmp = aircraftService.getAllAircrafts();
        List<Flight> tmp2 = flightService.getAllFlights();
        for (Flight fl : tmp2) {
            for (Aircraft cr : tmp) {
                if (fl.getFlightNumber() == flight.getFlightNumber() && Objects.equals(cr.getAircraftID(), aircraftID)) {
                    fl.setAircraftID(cr.getAircraftID());
                    cr.setFlight(fl);
                    cr.getFlight().setAircraftID(cr.getAircraftID());
                    fl.setAircraft(cr);
                    break;
                }
            }
        }
        return "Added aircraft " + aircraftID + " to flight " + flight.getFlightNumber();
    }

    @Transactional
    public String takeOutAircraft(int flightNumber){
        List<Flight> tmp2 = flightService.getAllFlights();
        List<Aircraft> tmp3 = aircraftService.getAllAircrafts();
        for(Aircraft craft : tmp3){
            if(craft.getFlight() == null){
                break;
            }
            if(craft.getFlight().getFlightNumber() == flightNumber){
                craft.setFlight(null);
            }
        }
        for (Flight fl : tmp2) {
            if(fl.getFlightNumber() == flightNumber){
               fl.setAircraftID(null);
            }
        }
        return "removed aircraft from flight " + flightNumber;
    }

    @Transactional
    public String removeDest(int flightNumber){
        List<Flight> flights = flightService.getAllFlights();
        for (Flight tmp : flights) {
            if(tmp.getFlightNumber() == flightNumber){
                tmp.setDestination(null);
            }
        }
        return "removed destination from flight " + flightNumber;
    }

    @Transactional
    public String addDest(int flightNumber, String dest){
        List<Flight> flights = flightService.getAllFlights();
        for (Flight tmp : flights) {
            if(tmp.getFlightNumber() == flightNumber){
                tmp.setDestination(dest);
            }
        }
        return "added destination " + dest + " from flight " + flightNumber;
    }

}
