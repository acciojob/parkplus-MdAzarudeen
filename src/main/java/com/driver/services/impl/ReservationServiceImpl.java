package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        // get user and parking lot
        User user;
        ParkingLot parkingLot;
        try {
            user = userRepository3.findById(userId).get();
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        }catch (Exception exception) {
            throw new Exception("Cannot make reservation");
        }

        // get the spot as per given criteria
        List<Spot> spotList = parkingLot.getSpotList();
        Spot spot = null;
        int totalPrice = Integer.MAX_VALUE;

        for(Spot curSpot : spotList){
            if(!curSpot.getOccupied()){
                int currTotal = 0;
                if(numberOfWheels <= 2){
                    currTotal = timeInHours * curSpot.getPricePerHour();
                }
                else if(numberOfWheels <= 4 && !curSpot.getSpotType().toString().equals("TWO_WHEELER")){
                    currTotal = timeInHours * curSpot.getPricePerHour();
                }
                else if(numberOfWheels > 4 && curSpot.getSpotType().toString().equals("OTHERS")){
                    currTotal = timeInHours * curSpot.getPricePerHour();
                }
                if(currTotal != 0 && currTotal < totalPrice){
                    totalPrice = currTotal;
                    spot = curSpot;
                }
            }
        }

        if(spot == null){
            throw new Exception("Cannot make reservation");
        }

        // now make Reservation
        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        reservation.setSpot(spot);
        spot.setOccupied(true);

        user.getReservationList().add(reservation);
        spot.getReservationList().add(reservation);

        spotRepository3.save(spot);
        userRepository3.save(user);

        return reservation;
    }
}