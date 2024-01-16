package com.example.AirlineBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AirlineBackend.data.Payment;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.service.PaymentService;
import com.example.AirlineBackend.service.SeatService;

@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private PaymentService paymentService;
    private SeatService seatService;

    public PaymentController(PaymentService paymentService, SeatService seatService) {
        this.paymentService = paymentService;
        this.seatService = seatService;
    }

    @PostMapping(value = "/process", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> processPaymentAndSendEmails(@RequestBody Payment payment) {
        boolean paymentProcessed = paymentService.processPayment(payment);
        if (paymentProcessed) {
            return ResponseEntity.ok("Payment successful.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment processing failed.");
        }
    }

    @PostMapping(value = "/refund/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> refundPaymentAndSendEmail(@RequestBody Payment payment, @PathVariable Long id) {

        
        Seat seat = seatService.findSeat(id);
        System.out.println(seat);
        System.out.println(seat);
        System.out.println(seat);
        System.out.println(seat);
        System.out.println(seat);
        System.out.println(seat);



        payment.setSingleSeat(seat); // Assuming setSeats() expects a Seat object
        List<Seat> s = payment.getSeats();
        System.out.println(s);
        System.out.println(s);
        System.out.println(s);
        System.out.println(s);
        System.out.println(s);
        System.out.println(s);

        Integer amountRefunded = Integer.valueOf(paymentService.refundPayment(payment));
        if (amountRefunded != null) {
            return ResponseEntity.ok("Refund successful.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Refund failed.");
        }
    }
}
