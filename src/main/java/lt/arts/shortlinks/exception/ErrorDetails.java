package lt.arts.shortlinks.exception;

import lombok.Data;

import java.util.Date;

/**
 * The ErrorDetails class represents the structure of error response data
 * sent back to the client when an exception occurs in the application.
 */

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String errorCode;

    public ErrorDetails(Date timestamp, String message, String errorCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.errorCode = errorCode;
    }
}
