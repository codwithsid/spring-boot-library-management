package com.librarymanagement.demo.model;

import com.librarymanagement.demo.model.enums.ReviewSource;
import com.librarymanagement.demo.model.enums.ReviewType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewType reviewType;
    // "BOOK", "AUTHOR", "STORE"

    private double rating;

    @Column(length = 1000)
    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean isVerified; // true if posted by a valid user (e.g., borrower/member)

    @Enumerated(EnumType.STRING)
    private ReviewSource reviewSource;
    // "BORROWED", "MEMBER", "GUEST"
    @PrePersist
    public void setDefaults() {
        if (reviewSource == null) {
            reviewSource = ReviewSource.GUEST;
        }


        isVerified = reviewSource != ReviewSource.GUEST;
    }

}
