package com.example.AirlineBackend.data;

import java.io.IOException;

import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.service.FlightService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class CancellationEmailStrategy implements EmailStrategy {
    private FlightService flightService;
    private final String SENDGRID_API_KEY = "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";

    public CancellationEmailStrategy(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public void sendEmail(Payment payment) {
        Email from = new Email("liambrennan0411@gmail.com");
  
        Flight flight = flightService.getFlightId(payment.getFlightID());

        String subject = "Cancellation Notification for Flight " + payment.getFlightID() + " from " + flight.getDeparture() + " to " + flight.getDestination();
        Email to = new Email("liambrennan0411@gmail.com");

        StringBuilder cancellationContent = new StringBuilder();
        cancellationContent.append("Your booking for Flight " + payment.getFlightID() + " has been canceled and refunded.\n\n");

        int totalPrice = 0;

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
                default:
                    break;
            }

            String seatNumber = seat.getSeatNumber();
            cancellationContent.append("Seat ").append(seatNumber).append(": $").append(price).append("\n");
            totalPrice += price;
        }

        cancellationContent.append("\nTotal Refund Amount: $").append(totalPrice);

        Content content = new Content("text/plain", cancellationContent.toString());

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
}
