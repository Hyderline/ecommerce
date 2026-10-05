package com.simoneg.ecommerce.utils.exceptions;

import lombok.Getter;

import java.nio.file.AccessDeniedException;

@Getter
public class FieldNotAuthorizedException  extends AccessDeniedException {
    private final String field;

    public FieldNotAuthorizedException(String field) {
        super("You are not authorized to use the field: " + field);
        this.field = field;
    }

}
