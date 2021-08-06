package com.sp.addressbook.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NotFoundException extends ResponseStatusException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    @Override
    public HttpHeaders getResponseHeaders() {
        // return response headers
        return new HttpHeaders();
    }
}
