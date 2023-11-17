package com.example.BaseProject.exceptions;

public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String exMessage) {
        super(exMessage);
    }
}
