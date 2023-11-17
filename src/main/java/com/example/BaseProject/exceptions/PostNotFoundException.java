package com.example.BaseProject.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String exMessage) {
        super(exMessage);
    }
}
