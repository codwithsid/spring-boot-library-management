package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Recommendation;

import java.util.List;

public interface RecommendationService {
    List<Recommendation> getRecommendationsForUser(int userId);
    void generateRecommendation(int userId); // Auto-generated recs
}
