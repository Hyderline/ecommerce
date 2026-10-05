package com.simoneg.ecommerce.service;

import com.simoneg.ecommerce.dto.CreateUserRequestDto;
import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.dto.GetUsersResponseDto;
import com.simoneg.ecommerce.dto.UpdateUserRequestDto;
import com.simoneg.ecommerce.model.Users;
import com.simoneg.ecommerce.utils.exceptions.FieldNotAuthorizedException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UsersService {
    List<GetUsersResponseDto> getUsers(GetUsersRequestDto requestDto);
    void createUser(CreateUserRequestDto user);

    void getUserResponseDtoBuilder(List<Users> queryResult, List<GetUsersResponseDto> listGetUserResponse);

    boolean checkRoleAdmin(String username);

    void deleteUser(String username);

    void updateUser(UpdateUserRequestDto updateRequestDto, String apiUser) throws AccessDeniedException;

    void checkCanEditCompany(String apiUsername, Users targetUser) throws FieldNotAuthorizedException;
}
