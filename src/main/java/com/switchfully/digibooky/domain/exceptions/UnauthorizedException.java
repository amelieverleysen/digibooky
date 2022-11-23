package com.switchfully.digibooky.domain.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
