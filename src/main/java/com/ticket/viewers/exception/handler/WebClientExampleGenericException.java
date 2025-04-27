package com.ticket.viewers.exception.handler;

public class WebClientExampleGenericException extends RuntimeException{

    private String message;
    private Integer status;

    public WebClientExampleGenericException(String message) {
        super(message);
        this.message = message;
    }

    public WebClientExampleGenericException(String message, Integer status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
