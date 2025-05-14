package com.librarymanagement.demo.repository;

import com.librarymanagement.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.borrowedBy IS NOT NULL AND b.returnDate IS NULL AND b.borrowDate <= :reminderDate")
    List<Book> findAllBorrowedBooksNotReturnedBefore(LocalDateTime reminderDate);
    List<Book> findTop5ByOrderByBorrowCountDesc();

}