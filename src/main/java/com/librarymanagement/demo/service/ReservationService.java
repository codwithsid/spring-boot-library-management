package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation markReservation(int userId, int bookId);
    Reservation updateStatus(int reservationId, String status);
    List<Reservation> getReservationsByUser(int userId);
    List<Reservation> getAllReservations();
    void cancelReservation(int reservationId);
}
