package com.ticket.viewers.exception.handler;

public class ViwersGenericException extends RuntimeException{

    private String message;

    public ViwersGenericException(String message, String message1) {
        super(message);
        this.message = message1;
    }
}
