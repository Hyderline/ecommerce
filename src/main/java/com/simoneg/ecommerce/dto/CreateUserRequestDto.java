package com.simoneg.ecommerce.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String roleName;

    private String companyName;
}
