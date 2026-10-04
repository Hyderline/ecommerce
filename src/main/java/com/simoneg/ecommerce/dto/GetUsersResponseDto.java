package com.simoneg.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUsersResponseDto {

    private String username;

    private String email;

    private Instant createdAt;

    private Instant updatedAt;

    private String roleName;

    private List<String> permissions;

    private String companyName;

    public GetUsersResponseDto() {

    }

}
