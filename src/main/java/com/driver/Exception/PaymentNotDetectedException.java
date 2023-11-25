package com.driver.Exception;

public class PaymentNotDetectedException extends RuntimeException{
    public PaymentNotDetectedException(String msg)
    {
        super(msg);
    }
}
