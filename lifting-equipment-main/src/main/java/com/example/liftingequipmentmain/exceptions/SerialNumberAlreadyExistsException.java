package com.example.liftingequipmentmain.exceptions;

public class SerialNumberAlreadyExistsException extends Exception {

    public SerialNumberAlreadyExistsException(String serialNumber) {
        super("Equipment with serial number " + serialNumber + " already exists.");
    }
}
