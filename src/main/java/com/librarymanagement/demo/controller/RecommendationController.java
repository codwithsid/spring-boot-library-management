package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Recommendation;
import com.librarymanagement.demo.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public List<Recommendation> getUserRecommendations(@PathVariable int userId) {
        return recommendationService.getRecommendationsForUser(userId);
    }

    @PostMapping("/generate/{userId}")
    public String generateAndSendRecommendations(@PathVariable int userId) {
        recommendationService.generateRecommendation(userId);
        return "Recommendations generated and sent successfully.";
    }
}
