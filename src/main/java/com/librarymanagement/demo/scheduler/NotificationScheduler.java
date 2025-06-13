package com.librarymanagement.demo.scheduler;

import com.librarymanagement.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NotificationScheduler {

    private static final Logger log = LoggerFactory.getLogger(NotificationScheduler.class);

    @Autowired
    private NotificationService notificationService;

    /**
     * This scheduler runs daily at 9 AM and sends return reminders
     * for books borrowed more than 7 days ago and not yet returned.
     */
    @Scheduled(cron = "0 0 9 * * *") // Every day at 9 AM
    public void scheduleReturnReminders() {
        notificationService.sendReminderNotifications();

    }
}