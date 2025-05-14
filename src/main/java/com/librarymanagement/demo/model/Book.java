package com.librarymanagement.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String title;
    private String isbn;
    private LocalDateTime publishDate;
    private int totalCopies;
    private int availableCopies;
    private String category;
    private String language;
    private double price;
    private boolean isAvailable;

    @Column(name = "borrow_count")
    private int borrowCount;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private BookPublisher publisher;

    @ManyToOne
    @JoinColumn(name = "borrowed_by")
    private User borrowedBy;

    private LocalDateTime returnDate;

    private LocalDateTime borrowDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Transactions> transactions;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Recommendation> recommendations;
}

