package com.driver.services.impl;

import com.driver.Exception.CannotMakeReservationException;
import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.driver.model.SpotType.*;

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

            User user = userRepository3.findById(userId).get();
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        if(parkingLot==null || user==null )
        {
            throw new CannotMakeReservationException(String.format("Cannot Make Reservation"));
        }
            Spot spot = null;
            List<Spot> spotList = parkingLot.getSpotList();
        int totalPrice = Integer.MAX_VALUE;
        for(Spot curSpot : spotList){
            if(!curSpot.isOccupied()){
                int currTotal = 0;
                if(numberOfWheels == 2){
                    currTotal = timeInHours * curSpot.getPricePerHour();
                }
                else if(numberOfWheels == 4 && !curSpot.getSpotType().toString().equals("TWO_WHEELER")){
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
        if(spot==null )
        {
            throw new CannotMakeReservationException(String.format("Cannot Make Reservation"));
        }

        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setUser(user);
        reservation.setSpot(spot);
        spot.setOccupied(true);

        user.getReservationList().add(reservation);
        spot.getReservationList().add(reservation);

        spotRepository3.save(spot);
        userRepository3.save(user);


        return  reservation;
    }
}
