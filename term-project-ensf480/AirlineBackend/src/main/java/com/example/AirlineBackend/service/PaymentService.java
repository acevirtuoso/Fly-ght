package com.example.AirlineBackend.service;

import com.example.AirlineBackend.data.CreditCard;
import com.example.AirlineBackend.data.Payment;

public interface PaymentService {
    public boolean processPayment(Payment payment);

    public boolean verifyCreditCard(CreditCard creditCard);

    public int refundPayment(Payment payment);
}