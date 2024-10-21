package lt.arts.shortlinks.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

import static lt.arts.shortlinks.utils.ErrorCodes.*;

/**
 * GlobalExceptionHandler is a centralized error-handling component for handling exceptions
 * that occur in the application. It uses the @RestControllerAdvice annotation to catch
 * exceptions across all controller layers and provides appropriate HTTP responses.
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleInvalidUrlException(InvalidUrlException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), INVALID_URL_FORMAT);
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDetails> handleInvalidUrlException(UrlNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), URL_NOT_FOUND);
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDetails> handleGeneralException(Exception ex) {
        ErrorDetails errorResponse = new ErrorDetails(new Date(), ex.getMessage(), INTERNAL_SERVER_ERROR);
        log.error(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
