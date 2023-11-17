package com.example.BaseProject.exceptions;

public class UsernameNotFoundException extends RuntimeException{
    public UsernameNotFoundException(String exMessage) {
        super(exMessage);
    }
}
