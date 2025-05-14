package com.librarymanagement.demo.controller;

import com.librarymanagement.demo.model.Review;
import com.librarymanagement.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-management/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/submit")
    public Review submitReview(
            @RequestBody Review review,
            @RequestParam int userId,
            @RequestParam(required = false) Integer bookId,
            @RequestParam(required = false) Integer authorId,
            @RequestParam(required = false) Integer storeId
    ) {
        return reviewService.submitReview(review, userId, bookId, authorId, storeId);
    }

    @GetMapping("/user/{userId}")
    public List<Review> getReviewsByUser(@PathVariable int userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @GetMapping("/book/{bookId}")
    public List<Review> getReviewsByBook(@PathVariable int bookId) {
        return reviewService.getReviewsByBook(bookId);
    }

    @GetMapping("/author/{authorId}")
    public List<Review> getReviewsByAuthor(@PathVariable int authorId) {
        return reviewService.getReviewsByAuthor(authorId);
    }

    @GetMapping("/store/{storeId}")
    public List<Review> getReviewsByStore(@PathVariable int storeId) {
        return reviewService.getReviewsByStore(storeId);
    }

    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable int reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
