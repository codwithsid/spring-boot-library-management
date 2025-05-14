package com.librarymanagement.demo.exception.reservationException;

public class BookAlreadyReservedException extends RuntimeException {
    public BookAlreadyReservedException(String message) {
        super(message);
    }
}
