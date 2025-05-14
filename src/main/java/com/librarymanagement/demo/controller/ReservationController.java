package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Reservation;
import com.librarymanagement.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/mark")
    public Reservation markReservation(@RequestParam int userId, @RequestParam int bookId) {
        return reservationService.markReservation(userId, bookId);
    }

    @PatchMapping("/{id}/status")
    public Reservation updateStatus(@PathVariable int id, @RequestParam String status) {
        return reservationService.updateStatus(id, status);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getByUser(@PathVariable int userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAllReservations();
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable int id) {
        reservationService.cancelReservation(id);
    }
}
