package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Review;
import com.librarymanagement.demo.model.enums.ReviewType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByUser_UserId(int userId);
    List<Review> findByReviewType(ReviewType type);
    List<Review> findByBook_BookId(int bookId);
    List<Review> findByAuthor_AuthorId(int authorId);
    List<Review> findByStore_StoreId(int storeId);
}
