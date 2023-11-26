package com.driver.model;

import javax.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private int hours;
    @ManyToOne
    private User user;
    @ManyToOne
    private Spot spot;
    @OneToOne
    private Payment payment;

    public Reservation() {
    }

    public Reservation(int id, int hours, User user, Spot spot, Payment payment) {
        this.id = id;
        this.hours = hours;
        this.user = user;
        this.spot = spot;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
