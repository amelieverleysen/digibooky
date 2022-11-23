package com.switchfully.digibooky.domain.exceptions;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException() {
        super("Unauthorized");
    }
}
