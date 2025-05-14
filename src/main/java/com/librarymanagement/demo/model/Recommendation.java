package com.librarymanagement.demo.model;

import com.librarymanagement.demo.model.enums.Source;
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
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recommendationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private String reason; // "Based on history", "Popular among similar readers"
    @Enumerated(EnumType.STRING)
    private Source source; // Optional: AI, Manual, Rule-Based

    @CreationTimestamp
    private LocalDateTime createdAt;
}

