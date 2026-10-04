package com.simoneg.ecommerce.enumeration;

import lombok.Getter;

@Getter
public enum RolesEnum {
    ADMIN("ADMIN"),
    SELLER("SELLER"),
    USER("USER");

    private final String value;

    RolesEnum(String value) {
        this.value = value;
    }

}
