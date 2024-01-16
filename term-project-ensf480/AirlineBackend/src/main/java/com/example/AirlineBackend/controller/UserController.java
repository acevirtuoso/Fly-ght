package com.example.AirlineBackend.controller;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.model.Ticket;
import com.example.AirlineBackend.model.User;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController{
    private UserService userService;
    private FlightService flightService;
    private SeatService seatService;
    public UserController(UserService userService, FlightService flightService, SeatService seatService){
        super();
        this.userService = userService;
        this.flightService = flightService;
        this.seatService = seatService;
    }

    @PostMapping(value = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User add(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/getUser")
    public User getUser(@RequestBody HashMap<String, String> email){
        System.out.println("goes in getUser");
        System.out.println("goes in getUser");
        System.out.println(email.entrySet());
        System.out.println("goes in getUser");
        System.out.println("goes in getUser");
        
        return userService.getUser(email.get("email"));
    }

    @DeleteMapping("/deleteAllUsers")
    public void deleteAllUsers(){
        userService.deleteAllUsers();
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(Long flightId){
        userService.deleteUser(flightId);
    }

    @PostMapping("/getUsersSeats")  //change to PostMapping later
    public List<Seat> getUsersSeats(@RequestBody HashMap<String, String> user) {
        return seatService.getUsersSeats(user.get("email"));
    }

    @PostMapping("/deleteBookedSeat")
    public String deleteSeat(@RequestBody HashMap<String, Long> seatId){  // the argument was  @RequestBody String string
        // System.out.println(seatId);
        seatService.deleteUsersSeat(seatId.get("id"));
        return "hello";
    }

    @PostMapping(value = "/getUsersTickets")
    public List<Ticket> getTickets(@RequestBody HashMap<String, String> email){  // the argument was  @RequestBody String string
        //userService.getUsersTickets("default@email.com");
        return seatService.getUsersTickets(email.get("email"));
    }

    @PutMapping("/takeMoney/{totalPrice}/{email}")
    public String takeMoney(@PathVariable double totalPrice, @PathVariable String email){
        // System.out.println(email);
        // System.out.println(totalPrice);
        //  System.out.println(email);
        // System.out.println(totalPrice);
        //  System.out.println(email);
        // System.out.println(totalPrice);
        return userService.manageBalance(email, totalPrice);
    }

    @PutMapping("/refundMoney/{price}/{email}")
    public String refund(@PathVariable double price, @PathVariable String email){
        // System.out.println(email);
        // System.out.println(price);
        //  System.out.println(email);
        // System.out.println(price);
        //  System.out.println(email);
        // System.out.println(price);
        return userService.refundBalance(email, price);
    }
}
