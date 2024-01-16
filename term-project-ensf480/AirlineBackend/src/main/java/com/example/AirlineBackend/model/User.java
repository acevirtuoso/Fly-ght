package com.example.AirlineBackend.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String passwordKey;
    private String userRole;
    private double balance;

    //private Ticket tickets[];

    public User(String name, String email, String password, String userRole) {
        this.name = name;
        this.email = email;
        this.passwordKey = password;
        this.userRole = userRole;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String Name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return passwordKey;
    }

    public void setPassword(String password) {
        this.passwordKey = password;
    }

    public String getRole() {
        return userRole;
    }

    public void setRole(String role) {
        this.userRole = role;
    }

    public String getName() {
        return name;
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void subtractBalance(double num){
        setBalance(balance - num);
    }
}
