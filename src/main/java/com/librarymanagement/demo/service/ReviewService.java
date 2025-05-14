package com.librarymanagement.demo.service;

import com.librarymanagement.demo.model.Review;

import java.util.List;

public interface ReviewService {
    Review submitReview(Review review, int userId, Integer bookId, Integer authorId, Integer storeId);
    List<Review> getReviewsByUser(int userId);
    List<Review> getReviewsByBook(int bookId);
    List<Review> getReviewsByAuthor(int authorId);
    List<Review> getReviewsByStore(int storeId);
    Review getReviewById(int reviewId);
    void deleteReview(int reviewId);
}
