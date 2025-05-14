package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.recommandationException.RecommendationNotFoundException;
import com.librarymanagement.demo.model.*;
import com.librarymanagement.demo.model.enums.DeliveryChannel;
import com.librarymanagement.demo.model.enums.Source;
import com.librarymanagement.demo.repository.*;
import com.librarymanagement.demo.service.NotificationService;
import com.librarymanagement.demo.service.RecommendationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<Recommendation> getRecommendationsForUser(int userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserUserId(userId);
        if (recommendations.isEmpty()) {
            throw new RecommendationNotFoundException("No recommendations found for user with ID: " + userId);
        }
        return recommendations;
    }

    @Override
    @Transactional
    public void generateRecommendation(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecommendationNotFoundException("User not found with ID: " + userId));

        // Fetch popular books or any logic here
        List<Book> topBooks = bookRepository.findTop5ByOrderByBorrowCountDesc();

        for (Book book : topBooks) {
            Recommendation recommendation = new Recommendation();
            recommendation.setUser(user);
            recommendation.setBook(book);
            recommendation.setReason("Popular among similar readers");
            recommendation.setSource(Source.AI);
            recommendation.setCreatedAt(LocalDateTime.now());

            recommendationRepository.save(recommendation);

            // Send notification
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setTitle("Book Recommendation");
            notification.setMessage("We recommend you read: " + book.getTitle());
            notification.setSentAt(LocalDateTime.now());
            notification.setDeliveryChannel(DeliveryChannel.EMAIL);
            notification.setNotificationType("RECOMMENDATION");
            notification.setRead(false);

            notificationService.sendNotification(notification);
        }
    }
}
