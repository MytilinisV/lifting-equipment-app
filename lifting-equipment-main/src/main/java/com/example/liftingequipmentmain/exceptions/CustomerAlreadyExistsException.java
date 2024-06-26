package com.example.liftingequipmentmain.exceptions;

import java.math.BigInteger;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(BigInteger tin) {
        super("Customer with Tax Identification Number " + tin + " already exists.");
    }
}
