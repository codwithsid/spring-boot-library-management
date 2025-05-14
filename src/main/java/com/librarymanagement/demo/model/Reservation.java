package com.librarymanagement.demo.model;

import com.librarymanagement.demo.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDateTime reservationDate;
    private LocalDateTime expiryDate;

    private boolean fulfilled;  // Alternatively use enum below
    private Status status;      // e.g., PENDING, CANCELLED, COMPLETED

    @CreationTimestamp
    private LocalDateTime createdAt;
}
