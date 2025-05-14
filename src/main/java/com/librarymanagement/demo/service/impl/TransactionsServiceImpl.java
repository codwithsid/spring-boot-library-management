package com.librarymanagement.demo.service.impl;

import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.exception.transactionException.TransactionNotFoundException;
import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.model.Book;
import com.librarymanagement.demo.model.Transactions;
import com.librarymanagement.demo.model.User;
import com.librarymanagement.demo.repository.BookRepository;
import com.librarymanagement.demo.repository.TransactionsRepository;
import com.librarymanagement.demo.repository.UserRepository;
import com.librarymanagement.demo.service.TransactionsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public TransactionsServiceImpl(TransactionsRepository transactionsRepository,
                                   BookRepository bookRepository,
                                   UserRepository userRepository) {
        this.transactionsRepository = transactionsRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transactions createTransaction(Transactions transaction) {
        // Validate book
        Book book = bookRepository.findById(transaction.getBook().getBookId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + transaction.getBook().getBookId()));

        // Validate user
        User user = userRepository.findById(transaction.getUser().getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + transaction.getUser().getUserId()));

        // Set validated entities
        transaction.setBook(book);
        transaction.setUser(user);
        transaction.setBorrowDate(LocalDateTime.now());
        transaction.setReturned(false);

        // Save transaction
        Transactions savedTransaction = transactionsRepository.save(transaction);

        // Increment borrow count of the book
        book.setBorrowCount(book.getBorrowCount() + 1);
        bookRepository.save(book);

        return savedTransaction;
    }

    @Override
    public Transactions getTransactionById(int transactionId) {
        return transactionsRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with ID: " + transactionId));
    }

    @Override
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    @Override
    public Transactions updateTransaction(Transactions transaction) {
        if (!transactionsRepository.existsById(transaction.getTransactionId())) {
            throw new TransactionNotFoundException("Cannot update non-existing transaction with ID: " + transaction.getTransactionId());
        }
        return transactionsRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(int transactionId) {
        if (!transactionsRepository.existsById(transactionId)) {
            throw new TransactionNotFoundException("Cannot delete non-existing transaction with ID: " + transactionId);
        }
        transactionsRepository.deleteById(transactionId);
    }
}
