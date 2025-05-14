package com.librarymanagement.demo.exception.reservationException;


public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
