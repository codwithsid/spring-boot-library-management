package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.exception.reservationException.BookAlreadyReservedException;
import com.librarymanagement.demo.exception.reservationException.ReservationNotFoundException;
import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.*;
import com.librarymanagement.demo.model.enums.DeliveryChannel;
import com.librarymanagement.demo.model.enums.Status;
import com.librarymanagement.demo.repository.BookRepository;
import com.librarymanagement.demo.repository.ReservationRepository;
import com.librarymanagement.demo.repository.UserRepository;
import com.librarymanagement.demo.repository.NotificationRepository;
import com.librarymanagement.demo.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String EMAIL_API_URL = "http://localhost:8080/api/v1/email-service/send";

    @Override
    @Transactional
    public Reservation markReservation(int userId, int bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID " + bookId));

        // Check if the book is already reserved
        if (reservationRepository.existsByBookAndStatusAndFulfilled(book, Status.PENDING, false)) {
            throw new BookAlreadyReservedException("Book is already reserved.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID " + userId));

        // Create new reservation
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(Status.PENDING);  // Set reservation status as PENDING
        reservation.setFulfilled(false);  // Reservation is not yet fulfilled
        reservation.setExpiryDate(LocalDateTime.now().plusDays(7));  // Set reservation expiry date (example: 7 days from now)

        reservationRepository.save(reservation);

        // Create a notification
        String message = String.format("You have successfully reserved the book titled '%s'. We will notify you once it's available.", book.getTitle());
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Book Reserved Successfully");
        notification.setMessage(message);
        notification.setNotificationType("RESERVATION");
        notification.setDeliveryChannel(DeliveryChannel.EMAIL);
        notification.setRead(false);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);

        // Prepare email request
        EmailRequest emailRequest = new EmailRequest(user.getEmailId(), notification.getTitle(), message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailRequest> request = new HttpEntity<>(emailRequest, headers);
        restTemplate.postForEntity(EMAIL_API_URL, request, String.class);

        return reservation;
    }

    @Override
    @Transactional
    public Reservation updateStatus(int reservationId, String status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID " + reservationId));

        // Update the status of the reservation
        reservation.setStatus(Status.valueOf(status.toUpperCase()));
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByUser(int userId) {
        return reservationRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional
    public void cancelReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID " + reservationId));

        // Cancel reservation and update status
        reservation.setStatus(Status.CANCELLED);
        reservation.setFulfilled(false);
        reservationRepository.save(reservation);

        // Create a notification
        User user = reservation.getUser();
        String message = String.format("Your reservation for the book titled '%s' has been cancelled.", reservation.getBook().getTitle());
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Reservation Cancelled");
        notification.setMessage(message);
        notification.setNotificationType("CANCELLATION");
        notification.setDeliveryChannel(DeliveryChannel.EMAIL);
        notification.setRead(false);
        notification.setSentAt(LocalDateTime.now());
        notificationRepository.save(notification);

        // Prepare email request
        EmailRequest emailRequest = new EmailRequest(user.getEmailId(), notification.getTitle(), message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EmailRequest> request = new HttpEntity<>(emailRequest, headers);
        restTemplate.postForEntity(EMAIL_API_URL, request, String.class);
    }
}
