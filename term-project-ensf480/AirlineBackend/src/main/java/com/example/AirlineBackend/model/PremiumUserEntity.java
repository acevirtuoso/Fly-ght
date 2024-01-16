package com.example.AirlineBackend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("premium")
@Table(name = "PremiumUsers", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class PremiumUserEntity extends User {
    private boolean voucher;
    private LocalDate lastVoucherUsage;
    private boolean creditCard;
    private boolean membership;
    private boolean monthlyNews;


    public PremiumUserEntity(String name, String email, String password, String userRole, boolean voucher, boolean creditCard, boolean membership, boolean monthlyNews) {
        super(name, email, password, userRole);
        this.voucher = voucher;
        this.lastVoucherUsage = LocalDate.now();
        this.creditCard = creditCard;
        this.membership = membership;
        this.monthlyNews = monthlyNews;
    }

    public boolean isVoucher() {
        return voucher;
    }

    public void setVoucher(boolean voucher) {
        this.voucher = voucher;
    }

    public LocalDate getLastVoucherUsage() {
        return lastVoucherUsage;
    }

    public void setLastVoucherUsage(LocalDate lastVoucherUsage) {
        this.lastVoucherUsage = lastVoucherUsage;
    }

    public boolean isCreditCard() {
        return creditCard;
    }

    public void setCreditCard(boolean creditCard) {
        this.creditCard = creditCard;
    }

    public boolean isMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public boolean isMonthlyNews() {
        return monthlyNews;
    }

    public void setMonthlyNews(boolean monthlyNews) {
        this.monthlyNews = monthlyNews;
    }

    public PremiumUserEntity() {
        super();
    }
}


