package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.exception.reviewException.ReviewNotFoundException;
import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.exception.reviewException.InvalidReviewTargetException;
import com.librarymanagement.demo.model.*;
import com.librarymanagement.demo.repository.*;
import com.librarymanagement.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Review submitReview(Review review, int userId, Integer bookId, Integer authorId, Integer storeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
        review.setUser(user);

        boolean hasTarget = false;

        if (bookId != null) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found"));
            review.setBook(book);
            review.setReviewType(com.librarymanagement.demo.model.enums.ReviewType.BOOK);
            hasTarget = true;
        }

        if (authorId != null) {
            Author author = authorRepository.findById(authorId)
                    .orElseThrow(() -> new RuntimeException("Author with ID " + authorId + " not found"));
            review.setAuthor(author);
            review.setReviewType(com.librarymanagement.demo.model.enums.ReviewType.AUTHOR);
            hasTarget = true;
        }

        if (storeId != null) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("Store with ID " + storeId + " not found"));
            review.setStore(store);
            review.setReviewType(com.librarymanagement.demo.model.enums.ReviewType.STORE);
            hasTarget = true;
        }

        if (!hasTarget) {
            throw new InvalidReviewTargetException("Review must target at least one entity (book, author, or store)");
        }

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByUser(int userId) {
        return reviewRepository.findByUser_UserId(userId);
    }

    @Override
    public List<Review> getReviewsByBook(int bookId) {
        return reviewRepository.findByBook_BookId(bookId);
    }

    @Override
    public List<Review> getReviewsByAuthor(int authorId) {
        return reviewRepository.findByAuthor_AuthorId(authorId);
    }

    @Override
    public List<Review> getReviewsByStore(int storeId) {
        return reviewRepository.findByStore_StoreId(storeId);
    }

    @Override
    public Review getReviewById(int reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID " + reviewId + " not found"));
    }

    @Override
    public void deleteReview(int reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ReviewNotFoundException("Review with ID " + reviewId + " does not exist");
        }
        reviewRepository.deleteById(reviewId);
    }
}
