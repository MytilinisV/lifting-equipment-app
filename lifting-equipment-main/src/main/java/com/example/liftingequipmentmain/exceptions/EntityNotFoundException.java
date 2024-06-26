package com.example.liftingequipmentmain.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends Exception {
//    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(Class<?> entityClass) {
        super("Entity " + entityClass.getSimpleName() + " does not exist");
    }
}
