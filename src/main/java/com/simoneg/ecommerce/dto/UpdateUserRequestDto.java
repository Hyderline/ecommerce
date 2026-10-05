package com.simoneg.ecommerce.dto;

import com.simoneg.ecommerce.enumeration.RolesEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {

    @NotEmpty(message = "Must be present")
    @Size(min = 6, max = 100)
    private String username;

    @NotBlank(message = "Must be present")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Must have a valid format for an email")
    private String email;

    @NotEmpty
    @Size(min = 8, max = 64, message = "Length must be between: 8-64 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9\\s]).+$",
            message = "Must have: 1 lowercase, 1 uppercase, 1 number e 1 special character"
    )
    private String password;

    private RolesEnum roleName;

    private String companyName;

}
