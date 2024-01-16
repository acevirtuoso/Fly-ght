package com.example.AirlineBackend.implementation;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.AirlineBackend.data.CancellationEmailStrategy;
import com.example.AirlineBackend.data.CreditCard;
import com.example.AirlineBackend.data.Payment;
import com.example.AirlineBackend.data.ReceiptEmailStrategy;
import com.example.AirlineBackend.data.TicketEmailStrategy;
import com.example.AirlineBackend.model.Flight;
import com.example.AirlineBackend.model.Seat;
import com.example.AirlineBackend.repository.FlightRepository;
import com.example.AirlineBackend.service.EmailService;
import com.example.AirlineBackend.service.FlightService;
import com.example.AirlineBackend.service.PaymentService;
import com.example.AirlineBackend.service.SeatService;
import com.example.AirlineBackend.service.UserService;

@Service
public class PaymentServiceImp implements PaymentService {
    private FlightRepository flightRepository;
    private EmailService emailService;
    private FlightService flightService;
    private SeatService seatService;
    public UserService userService;

    public PaymentServiceImp(FlightRepository flightRepository, EmailService emailService, FlightService flightService, SeatService seatService, UserService userService) {
        this.flightRepository = flightRepository;
        this.emailService = emailService;
        this.flightService = flightService;
        this.seatService = seatService;
        this.userService = userService;
    }

    public boolean processPayment(Payment payment) {
        boolean cardVerified = verifyCreditCard(payment.getCreditCard());
        int subtractedAmount = 0;
        if (cardVerified) {
            Flight flight = flightService.getFlightId(payment.getFlightID());
            if (flight != null) {
                for (Seat takenSeat : payment.getSeats()) {
                    takenSeat.setName(payment.getName());
                    takenSeat.setInsured(payment.isHasTicketInsurance());
                    takenSeat.setSeatAvailable(false);
                    takenSeat.setSeatBookedBy(payment.getEmail());
                    takenSeat.setFlightNumber(payment.getFlightNumber().intValue());
                    seatService.saveSeat(takenSeat);
                    String seatType = takenSeat.getSeatType();
                    switch (seatType) {
                        case "Regular":
                            subtractedAmount += 100;
                            break;
                        case "Comfort":
                            subtractedAmount += 150;
                            break;
                        case "Business":
                            subtractedAmount += 200;
                            break;
                        
                        default:
                            break;
                    }
                }
                userService.manageBalance(payment.getEmail(), subtractedAmount);
                emailService.sendEmail(payment, new ReceiptEmailStrategy(flightService));
                emailService.sendEmail(payment, new TicketEmailStrategy(flightService));
                return true;
                
            }
        }
        return false;
    }

    @Override
	public boolean verifyCreditCard(CreditCard creditCard) {
		String cardNumberString = String.valueOf(creditCard.getCardNumber());
		if (cardNumberString.length() != 16) {
			return false;
		}

		String cvvString = String.valueOf(creditCard.getCvv());
		if (cvvString.length() != 3) {
			return false;
		}

		Date currentDate = new Date();
		if (creditCard.getExpiryDate().after(currentDate)) {
			return false;
		}

		return true;
	}

    @Override
    public int refundPayment(Payment payment) {
        System.out.println(payment.getFlightNumber());
        System.out.println(payment.getFlightID());
        System.out.println(payment.getName());
        System.out.println(payment.getEmail());
        System.out.println(payment.getCreditCard());
        System.out.println(payment.getSeats());
        int refundAmount = 0;
        for (Seat refundedSeat : payment.getSeats()) {
            String seatType = refundedSeat.getSeatType();
            switch (seatType) {
                case "Regular":
                    refundAmount += 100;
                    break;
                case "Comfort":
                    refundAmount += 150;
                    break;
                case "Business":
                    refundAmount += 200;
                    break;
                
                default:
                    break;
            }
        }
        if (payment.isHasTicketInsurance()) {
            refundAmount += 100;
        }
        userService.refundBalance(payment.getEmail(), refundAmount);
        emailService.sendEmail(payment, new CancellationEmailStrategy(flightService));
        return refundAmount;
    }
}
