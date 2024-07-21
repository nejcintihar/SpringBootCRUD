package com.dev.product.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

public class CustomErrorResponse implements ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private List<String> details;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(String timestamp, int status, String error, List<String> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.valueOf(status);
    }

    @Override
    public ProblemDetail getBody() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(getStatusCode());
        problemDetail.setTitle(error);
        problemDetail.setDetail(String.join(", ", details));
        problemDetail.setProperty("timestamp", timestamp);
        return problemDetail;
    }
}