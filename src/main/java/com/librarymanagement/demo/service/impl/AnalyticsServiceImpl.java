package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.analyticsException.AnalyticsNotFoundException;
import com.librarymanagement.demo.model.Analytics;
import com.librarymanagement.demo.model.enums.EntityType;
import com.librarymanagement.demo.repository.AnalyticsRepository;
import com.librarymanagement.demo.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    @Autowired
    private AnalyticsRepository analyticsRepository;

    @Override
    public Analytics getAnalytics(EntityType entityType, int entityId) {
        return analyticsRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .orElseThrow(() -> new AnalyticsNotFoundException(
                        "Analytics not found for " + entityType + " with ID: " + entityId));
    }

    @Override
    @Transactional
    public void incrementView(EntityType entityType, int entityId) {
        Analytics analytics = analyticsRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .orElseGet(() -> createNewAnalytics(entityType, entityId));
        analytics.setViewCount(analytics.getViewCount() + 1);
        analyticsRepository.save(analytics);
    }

    @Override
    @Transactional
    public void incrementBorrow(EntityType entityType, int entityId) {
        Analytics analytics = analyticsRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .orElseGet(() -> createNewAnalytics(entityType, entityId));
        analytics.setBorrowCount(analytics.getBorrowCount() + 1);
        analyticsRepository.save(analytics);
    }

    @Override
    @Transactional
    public void addRating(EntityType entityType, int entityId, double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        Analytics analytics = analyticsRepository.findByEntityTypeAndEntityId(entityType, entityId)
                .orElseGet(() -> createNewAnalytics(entityType, entityId));

        int currentCount = analytics.getRatingCount();
        double currentAvg = analytics.getAverageRating();

        double newAvg = (currentAvg * currentCount + rating) / (currentCount + 1);

        analytics.setRatingCount(currentCount + 1);
        analytics.setAverageRating(newAvg);
        analyticsRepository.save(analytics);
    }

    private Analytics createNewAnalytics(EntityType entityType, int entityId) {
        Analytics analytics = new Analytics();
        analytics.setEntityType(entityType);
        analytics.setEntityId(entityId);
        analytics.setViewCount(0);
        analytics.setBorrowCount(0);
        analytics.setRatingCount(0);
        analytics.setAverageRating(0.0);
        return analyticsRepository.save(analytics);
    }
}
