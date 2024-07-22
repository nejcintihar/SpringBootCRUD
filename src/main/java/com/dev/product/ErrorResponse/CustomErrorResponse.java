package com.dev.product.ErrorResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.util.List;

/**
 * Custom error response class that implements the ErrorResponse interface.
 * Represents the structure of error responses returned by the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse implements ErrorResponse {
    /**
     * The timestamp of when the error occurred.
     */
    private String timestamp;

    /**
     * The HTTP status code of the error response.
     */
    private int status;

    /**
     * The error message or title.
     */
    private String error;

    /**
     * The list of details or additional information about the error.
     */
    private List<String> details;

    /**
     * Returns the HTTP status code of the error response.
     *
     * @return the HttpStatus object representing the status code
     */
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.valueOf(status);
    }

    /**
     * Returns the ProblemDetail object representing the error response body.
     *
     * @return the ProblemDetail object containing the error details
     */
    @Override
    public ProblemDetail getBody() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(getStatusCode());
        problemDetail.setTitle(error);
        problemDetail.setDetail(String.join(", ", details));
        problemDetail.setProperty("timestamp", timestamp);
        return problemDetail;
    }
}