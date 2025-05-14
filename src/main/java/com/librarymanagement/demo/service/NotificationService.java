package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Notification;

import java.util.List;

public interface NotificationService {

    void sendNotification(Notification notification);

    void markNotificationAsRead(int notificationId, int userId);

    List<Notification> getNotificationsForUser(int userId, boolean isRead);

    List<Notification> getNotificationsByType(int userId, String notificationType);

    void sendReminderNotifications(); // Example: sending reminders to users
}
