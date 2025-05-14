package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Analytics;
import com.librarymanagement.demo.model.enums.EntityType;
import com.librarymanagement.demo.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library-management/v1/analytics")
public class AnalyticsController {

    @Autowired
    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService){
        this.analyticsService=analyticsService;
    }


    @GetMapping("/{entityType}/{entityId}")
    public ResponseEntity<Analytics> getAnalytics(@PathVariable EntityType entityType, @PathVariable int entityId) {
        return ResponseEntity.ok(analyticsService.getAnalytics(entityType, entityId));
    }

    @PostMapping("/{entityType}/{entityId}/view")
    public ResponseEntity<String> incrementView(@PathVariable EntityType entityType, @PathVariable int entityId) {
        analyticsService.incrementView(entityType, entityId);
        return ResponseEntity.ok("View count incremented");
    }

    @PostMapping("/{entityType}/{entityId}/borrow")
    public ResponseEntity<String> incrementBorrow(@PathVariable EntityType entityType, @PathVariable int entityId) {
        analyticsService.incrementBorrow(entityType, entityId);
        return ResponseEntity.ok("Borrow count incremented");
    }

    @PostMapping("/{entityType}/{entityId}/rate")
    public ResponseEntity<String> addRating(@PathVariable EntityType entityType,
                                            @PathVariable int entityId,
                                            @RequestParam double rating) {
        analyticsService.addRating(entityType, entityId, rating);
        return ResponseEntity.ok("Rating added");
    }
}
