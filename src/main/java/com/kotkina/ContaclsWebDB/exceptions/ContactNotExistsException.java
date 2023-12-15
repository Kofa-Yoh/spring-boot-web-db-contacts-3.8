package com.kotkina.ContaclsWebDB.exceptions;

public class ContactNotExistsException extends RuntimeException {

    public ContactNotExistsException(String message) {
        super(message);
    }
}
