package com.switchfully.digibooky.domain.exceptions;

public class UnknownUserException extends RuntimeException{
    public UnknownUserException() {
        super("Unauthorized");
    }
}
