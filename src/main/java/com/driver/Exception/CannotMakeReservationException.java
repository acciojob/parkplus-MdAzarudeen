package com.driver.Exception;

public class CannotMakeReservationException extends RuntimeException{
    public CannotMakeReservationException(String msg)
    {
        super(msg);
    }
}
