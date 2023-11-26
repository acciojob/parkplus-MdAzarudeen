package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private SpotType spotType;
    private int price;
    private boolean occupied;
    @ManyToOne
    private ParkingLot parkingLot;
    @OneToMany
    private List<Reservation> reservationList;

    public Spot() {
    }

    public Spot(int id, SpotType spotType, int price, boolean occupied, ParkingLot parkingLot, List<Reservation> reservationList) {
        this.id = id;
        this.spotType = spotType;
        this.price = price;
        this.occupied = occupied;
        this.parkingLot = parkingLot;
        this.reservationList = reservationList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
