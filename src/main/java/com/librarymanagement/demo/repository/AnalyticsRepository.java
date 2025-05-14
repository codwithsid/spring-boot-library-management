package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Analytics;
import com.librarymanagement.demo.model.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnalyticsRepository extends JpaRepository<Analytics, Integer> {
    Optional<Analytics> findByEntityTypeAndEntityId(EntityType entityType, int entityId);
}
