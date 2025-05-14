package com.librarymanagement.demo.model;

import com.librarymanagement.demo.model.enums.EntityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Analytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int analyticsId;

    @Enumerated(EnumType.STRING)
    private EntityType entityType; // BOOK, AUTHOR, USER, etc.
    private int entityId;

    private int viewCount;
    private int borrowCount;
    private int ratingCount;
    private double averageRating;

    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
