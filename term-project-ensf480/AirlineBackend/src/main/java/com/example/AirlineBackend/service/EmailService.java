package com.example.AirlineBackend.service;

import com.example.AirlineBackend.data.EmailStrategy;
import com.example.AirlineBackend.data.Payment;

public interface EmailService {
    void sendEmail(Payment payment, EmailStrategy emailStrategy);
    
    // public void sendReceiptEmail(Payment payment);

    // public void sendTicketEmail(Payment payment);
}

