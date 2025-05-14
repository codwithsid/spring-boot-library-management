package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.notificationException.NotificationNotFoundException;
import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.model.EmailRequest;
import com.librarymanagement.demo.model.Notification;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.model.enums.DeliveryChannel;
import com.librarymanagement.demo.repository.BookRepository;
import com.librarymanagement.demo.repository.NotificationRepository;
import com.librarymanagement.demo.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String EMAIL_API_URL = "http://localhost:8080/sendmail"; // Adjust if needed

    @Override
    @Transactional
    public void sendNotification(Notification notification) {
        notification.setSentAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void markNotificationAsRead(int notificationId, int userId) {
        Notification notification = notificationRepository.findByIdAndUserId(notificationId, userId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID " + notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getNotificationsForUser(int userId, boolean isRead) {
        return notificationRepository.findByUserIdAndIsRead(userId, isRead);
    }

    @Override
    public List<Notification> getNotificationsByType(int userId, String notificationType) {
        return notificationRepository.findByUserIdAndNotificationType(userId, notificationType);
    }

    @Override
    public void sendReminderNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderDate = now.minusDays(7);

        List<Book> borrowedBooks = bookRepository.findAllBorrowedBooksNotReturnedBefore(reminderDate);

        for (Book book : borrowedBooks) {
            // Find the active transaction (book not yet returned)
            User user = book.getTransactions().stream()
                    .filter(tx -> !tx.isReturned()) // assuming you have a boolean field "isReturned"
                    .map(tx -> tx.getUser())
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                log.warn("No active borrower found for book ID {}", book.getBookId());
                continue;
            }

            // Build message
            String messageBody = generateReminderMessage(user, book);

            // Save Notification
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTitle("Book Return Reminder");
            notification.setMessage(messageBody);
            notification.setRead(false);
            notification.setSentAt(now);
            notification.setNotificationType("REMINDER");
            notification.setDeliveryChannel(DeliveryChannel.EMAIL);
            notificationRepository.save(notification);

            // Prepare EmailRequest
            EmailRequest emailRequest = new EmailRequest(
                    user.getEmailId(),
                    notification.getTitle(),
                    messageBody
            );

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<EmailRequest> entity = new HttpEntity<>(emailRequest, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(EMAIL_API_URL, entity, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    log.info("Reminder email sent successfully to {}", user.getEmailId());
                } else {
                    log.error("Failed to send reminder email to {}. Status: {}", user.getEmailId(), response.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Exception occurred while sending email to {}: {}", user.getEmailId(), e.getMessage(), e);
            }
        }
    }


    private String generateReminderMessage(User user, Book book) {
        return String.format(
                "Dear %s %s,\n\nThis is a gentle reminder to return the book titled '%s'. " +
                        "It has been more than 7 days since you borrowed it. Please return it as soon as possible " +
                        "to avoid any penalties.\n\nThank you,\nLibrary Management Team",
                user.getFirstName(), user.getLastName(), book.getTitle()
        );
    }
}
