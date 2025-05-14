package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Notification;
import com.librarymanagement.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        notificationService.sendNotification(notification);
        return ResponseEntity.ok("Notification sent successfully.");
    }

    @PostMapping("/{notificationId}/read/{userId}")
    public ResponseEntity<String> markAsRead(@PathVariable int notificationId, @PathVariable int userId) {
        notificationService.markNotificationAsRead(notificationId, userId);
        return ResponseEntity.ok("Notification marked as read.");
    }

    @GetMapping("/user/{userId}/status/{isRead}")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(@PathVariable int userId, @PathVariable boolean isRead) {
        List<Notification> notifications = notificationService.getNotificationsForUser(userId, isRead);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/type/{notificationType}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@PathVariable int userId, @PathVariable String notificationType) {
        List<Notification> notifications = notificationService.getNotificationsByType(userId, notificationType);
        return ResponseEntity.ok(notifications);
    }
}
