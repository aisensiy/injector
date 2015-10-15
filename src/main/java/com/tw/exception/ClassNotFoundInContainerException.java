package com.tw.exception;

public class ClassNotFoundInContainerException extends RuntimeException {
    public ClassNotFoundInContainerException(String message) {
        super(message);
    }
}
