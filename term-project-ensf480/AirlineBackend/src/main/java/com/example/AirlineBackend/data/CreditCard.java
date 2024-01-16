package com.example.AirlineBackend.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreditCard {
    private String cardNumber;
    private Date expiryDate;
    private String cvv;

    public CreditCard(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        setExpiryDate(expiryDate);
        this.cvv = cvv;
    }

    public void setExpiryDate(String expiryDateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

        try {
            Date parsedExpiryDate = dateFormat.parse(expiryDateStr);
            this.expiryDate = parsedExpiryDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }



    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
