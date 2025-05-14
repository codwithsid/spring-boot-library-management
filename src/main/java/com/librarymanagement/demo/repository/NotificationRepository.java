package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserIdAndIsRead(int userId, boolean isRead);

    Optional<Notification> findByIdAndUserId(int notificationId, int userId);

    List<Notification> findByUserIdAndNotificationType(int userId, String notificationType);

    List<Notification> findByUserIdAndIsReadAndNotificationType(int userId, boolean isRead, String notificationType);
}
