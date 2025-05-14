package com.librarymanagement.demo.model;

import com.librarymanagement.demo.model.enums.DeliveryChannel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String message;
    private boolean isRead;
    private String notificationType; // e.g., INFO, REMINDER, ALERT
    private LocalDateTime sentAt;
    @Enumerated(EnumType.STRING)
    private DeliveryChannel deliveryChannel;
}
