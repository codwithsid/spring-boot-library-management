package com.librarymanagement.demo.exception;

import com.librarymanagement.demo.exception.addressException.AddressNotFoundException;
import com.librarymanagement.demo.exception.analyticsException.AnalyticsNotFoundException;
import com.librarymanagement.demo.exception.authorException.AuthorNotFoundException;
import com.librarymanagement.demo.exception.billingException.BillNotFoundException;
import com.librarymanagement.demo.exception.bookException.BookNotFoundException;
import com.librarymanagement.demo.exception.bookPublisherException.BookPublisherNotFoundException;
import com.librarymanagement.demo.exception.notificationException.NotificationNotFoundException;
import com.librarymanagement.demo.exception.recommandationException.RecommendationNotFoundException;
import com.librarymanagement.demo.exception.reservationException.ReservationNotFoundException;
import com.librarymanagement.demo.exception.storeException.StoreNotFoundException;
import com.librarymanagement.demo.exception.transactionException.TransactionNotFoundException;
import com.librarymanagement.demo.exception.userException.UserNotFoundException;
import com.librarymanagement.demo.exception.reviewException.ReviewNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            AddressNotFoundException.class,
            AnalyticsNotFoundException.class,
            AuthorNotFoundException.class,
            BillNotFoundException.class,
            BookNotFoundException.class,
            BookPublisherNotFoundException.class,
            NotificationNotFoundException.class,
            RecommendationNotFoundException.class,
            ReservationNotFoundException.class,
            StoreNotFoundException.class,
            TransactionNotFoundException.class,
            UserNotFoundException.class,
            ReviewNotFoundException.class
    })
    public ResponseEntity<Object> handleNotFoundException(RuntimeException runtimeException) {
        return buildErrorResponse(runtimeException.getMessage(), HttpStatus.NOT_FOUND);
    }


//    @ExceptionHandler(AddressNotFoundException.class)
//    public ResponseEntity<Object> handleAddressNotFound(AddressNotFoundException addressNotFoundException){
//        return buildErrorResponse(addressNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(AnalyticsNotFoundException.class)
//    public ResponseEntity<Object> handleAnalyticsNotFound(AnalyticsNotFoundException analyticsNotFoundException){
//        return buildErrorResponse(analyticsNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//        }
//
//    @ExceptionHandler(AuthorNotFoundException.class)
//    public ResponseEntity<Object> handleAuthorNotFound(AuthorNotFoundException authorNotFoundException){
//        return buildErrorResponse(authorNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BillNotFoundException.class)
//    public ResponseEntity<Object> handleBillingNotFound(BillNotFoundException billNotFoundException){
//        return buildErrorResponse(billNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BookNotFoundException.class)
//    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException bookNotFoundException){
//        return buildErrorResponse(bookNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(BookPublisherNotFoundException.class)
//    public ResponseEntity<Object> handleBookPublisherNotFound(BookPublisherNotFoundException bookPublisherNotFoundException){
//        return buildErrorResponse(bookPublisherNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(NotificationNotFoundException.class)
//    public ResponseEntity<Object> handleNotificationNotFound(NotificationNotFoundException notificationNotFoundException){
//        return buildErrorResponse(notificationNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(RecommendationNotFoundException.class)
//    public ResponseEntity<Object> handleRecommendationNotFound(RecommendationNotFoundException recommendationNotFoundException){
//        return buildErrorResponse(recommendationNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(ReservationNotFoundException.class)
//    public ResponseEntity<Object> handleReservationNotFound(ReservationNotFoundException reservationNotFoundException){
//        return buildErrorResponse(reservationNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(StoreNotFoundException.class)
//    public ResponseEntity<Object> handleStoreNotFound(StoreNotFoundException storeNotFoundException){
//        return buildErrorResponse(storeNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(TransactionNotFoundException.class)
//    public ResponseEntity<Object> handleTransactionNotFound(TransactionNotFoundException transactionNotFoundException){
//        return buildErrorResponse(transactionNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException userNotFoundException) {
//        return buildErrorResponse(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(ReviewNotFoundException.class)
//    public ResponseEntity<Object> handleReviewNotFound(ReviewNotFoundException reviewNotFoundException) {
//        return buildErrorResponse(reviewNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
//    }


    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);
        return new ResponseEntity<>(errorDetails, status);
    }
}
