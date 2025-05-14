package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.model.Reservation;
import com.librarymanagement.demo.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Find a reservation by book and status
    Optional<Reservation> findByBookAndStatus(Book book, Status status);

    // Check if a reservation exists with a specific book, status, and fulfilled state
    boolean existsByBookAndStatusAndFulfilled(Book book, Status status, boolean fulfilled);

    // Get all reservations by a user's ID
    List<Reservation> findByUser_UserId(int userId);

    // Get all reservations by status
    List<Reservation> findByStatus(Status status);
}
