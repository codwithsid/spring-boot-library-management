package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUser_UserIdAndIsRead(int userId, boolean isRead);


    Optional<Notification> findByNotificationIdAndUser_UserId(int notificationId, int userId);

    List<Notification> findByUser_UserIdAndNotificationType(int userId, String notificationType);

    List<Notification> findByUser_UserIdAndIsReadAndNotificationType(int userId, boolean isRead, String notificationType);
}
