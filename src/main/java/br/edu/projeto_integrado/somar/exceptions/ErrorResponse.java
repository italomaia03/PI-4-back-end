package br.edu.projeto_integrado.somar.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {

    private Class<?> exceptionClass;
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime thrownAt;

    public <T> ErrorResponse(Class<?> exceptionClass, HttpStatus httpStatus, String message) {
        this.exceptionClass = exceptionClass;
        this.httpStatus = httpStatus;
        this.message = message;
        this.thrownAt = LocalDateTime.now();
    }

    public Class<?> getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(Class<?> exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getThrownAt() {
        return thrownAt;
    }

    public void setThrownAt(LocalDateTime thrownAt) {
        this.thrownAt = thrownAt;
    }
}
