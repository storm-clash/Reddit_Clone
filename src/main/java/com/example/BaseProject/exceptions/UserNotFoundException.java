package com.example.BaseProject.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String exMessage) {
        super(exMessage);
    }
}
