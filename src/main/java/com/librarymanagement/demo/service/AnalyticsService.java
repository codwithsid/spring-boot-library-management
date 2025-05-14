package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Analytics;
import com.librarymanagement.demo.model.enums.EntityType;

public interface AnalyticsService {

    Analytics getAnalytics(EntityType entityType, int entityId);

    void incrementView(EntityType entityType, int entityId);

    void incrementBorrow(EntityType entityType, int entityId);

    void addRating(EntityType entityType, int entityId, double rating);
}
