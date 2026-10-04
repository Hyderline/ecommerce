package com.simoneg.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class GetUsersRequestDto {

    private String username;

    private String email;

    private Instant createdFrom;

    private Instant createdTo;

    private Instant updatedFrom;

    private Instant updatedTo;

    private String roleName;

    private String companyName;

    public GetUsersRequestDto() {
    }
}
