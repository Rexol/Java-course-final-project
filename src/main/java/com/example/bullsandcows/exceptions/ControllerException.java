package com.example.bullsandcows.exceptions;

public class ControllerException extends Exception {
    public ControllerException(String str) {
        // Calling constructor of parent Exception
        super(str);
    }
}
