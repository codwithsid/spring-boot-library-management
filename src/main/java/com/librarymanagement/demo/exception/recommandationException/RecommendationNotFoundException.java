package com.librarymanagement.demo.exception.recommandationException;

public class RecommendationNotFoundException extends RuntimeException {
    public RecommendationNotFoundException(String message) {
        super(message);
    }
}
