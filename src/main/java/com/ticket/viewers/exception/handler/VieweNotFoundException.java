package com.ticket.viewers.exception.handler;

public class VieweNotFoundException extends RuntimeException{

    private String message;

    public VieweNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public VieweNotFoundException(String message, Throwable cause, String message1) {
        super(message, cause);
        this.message = message1;
    }
}
