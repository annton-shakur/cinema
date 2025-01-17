package com.example.cinema.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, BadCredentialsException ex) {
        super(message + ex.getMessage());
    }
}
