package com.sp.addressbook.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class ConflictException extends ResponseStatusException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

    @Override
    public HttpHeaders getResponseHeaders() {
        // return response headers
        return new HttpHeaders();
    }
}
