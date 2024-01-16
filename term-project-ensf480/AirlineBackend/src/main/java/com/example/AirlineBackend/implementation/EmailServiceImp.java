package com.example.AirlineBackend.implementation;

import com.example.AirlineBackend.data.EmailStrategy;
import com.example.AirlineBackend.data.Payment;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.service.EmailService;
import com.example.AirlineBackend.service.FlightService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImp implements EmailService {

    private FlightService flightService;


    public EmailServiceImp(FlightService flightService) {
        this.flightService = flightService;
    }

    private final String SENDGRID_API_KEY = "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";

    public void sendReceiptEmail(Payment payment) {
        Email from = new Email(payment.getEmail());
        System.out.println("asdsadaasdasdasdsad");
        System.out.println("asdsadaasdasdasdsad");
        System.out.println("asdsadaasdasdasdsad");
        System.out.println("asdsadaasdasdasdsad");
        System.out.println("asdsadaasdasdasdsad");
        System.out.println("asdsadaasdasdasdsad");
        System.out.println("asdsadaasdasdasdsad");
        Flight flight = flightService.getFlightId(payment.getFlightID());
        // System.out.println(flight.g);
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        System.out.println("ssssssssssssssssssss");
        String subject = "Receipt for your purchase of flight " + payment.getFlightID() + "from" + flight.getDeparture() + "to" + flight.getDestination();
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        // System.out.println("ssssssssssssssssssss");
        Email to = new Email(payment.getEmail());

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Thank you for your purchase. Here's your receipt:\n\n");

        int totalPrice = 0; // Initialize total price outside the loop

        for (Seat seat : payment.getSeats()) {
            String seatType = seat.getSeatType();
            int price = 0;

            switch (seatType) {
                case "Regular":
                    price = 100;
                    break;
                case "Comfort":
                    price = 150;
                    break;
                case "Business":
                    price = 200;
                    break;
                // Handle other seat types if needed
                default:
                    // Handle default case or unsupported seat types
                    break;
            }

            // Assuming you have the logic to retrieve seat number for each seat
            String seatNumber = seat.getSeatNumber();

            // Append seat details and price to the email content
            emailContent.append("Seat ").append(seatNumber).append(": $").append(price).append("\n");

            // Accumulate price to the total price
            totalPrice += price;
        }

        // Append the total price to the email content after the loop
        emailContent.append("\nTotal Price: $").append(totalPrice);

        Content content = new Content("text/plain", emailContent.toString());

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendTicketEmail(Payment payment) {
        Email from = new Email("liambrennan0411@gmail.com");
        String subject = "Ticket for your purchase of flight " + payment.getFlightID();
        Email to = new Email(payment.getEmail());
        Content content = new Content("text/plain", "Thank you for your payment. Here's your ticket...");

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void sendEmail(Payment payment, EmailStrategy emailStrategy) {
        emailStrategy.sendEmail(payment);
    }  
}
