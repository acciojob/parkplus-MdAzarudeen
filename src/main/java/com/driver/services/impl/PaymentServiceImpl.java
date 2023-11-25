package com.driver.services.impl;

import com.driver.Exception.CannotMakeReservationException;
import com.driver.Exception.InsufficientAmountException;
import com.driver.Exception.PaymentNotDetectedException;
import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        Spot spot = reservation.getSpot();
        if(amountSent<reservation.getHours()*spot.getPrice())
            throw new InsufficientAmountException(String.format("Insufficient Amount for Reservation"));

        if(!mode.equals("CARD") && !mode.equals("CASH") && !mode.equals("UPI"))
            throw new PaymentNotDetectedException(String.format("Payment mode not detected"));

        Payment payment = new Payment();
        mode=mode.toUpperCase();
        payment.setPaymentMode(PaymentMode.valueOf(mode));
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);
        reservation.setPayment(payment);
        reservationRepository2.save(reservation);
        return payment;
    }
}
