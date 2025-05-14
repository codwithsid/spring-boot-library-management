package com.librarymanagement.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate publishDate;
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

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Transactions> transactions;
}
