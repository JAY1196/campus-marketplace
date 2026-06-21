// Path: src/main/java/com/campus/marketplace/exception/ResourceNotFoundException.java

package com.campus.marketplace.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}