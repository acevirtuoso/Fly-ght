package com.example.AirlineBackend.service;

import com.example.AirlineBackend.model.User;
import com.example.AirlineBackend.repository.UserRepository;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUser(String email);
    void deleteAllUsers();
    void deleteUser(Long userId);
    String manageBalance(String email, double amountOwed);
    String refundBalance(String email, double refundAmount);
}
