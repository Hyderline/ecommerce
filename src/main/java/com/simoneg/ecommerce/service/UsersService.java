package com.simoneg.ecommerce.service;

import com.simoneg.ecommerce.dto.CreateUserRequestDto;
import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.dto.GetUsersResponseDto;
import com.simoneg.ecommerce.model.Users;

import java.util.List;

public interface UsersService {
    List<GetUsersResponseDto> getUsers(GetUsersRequestDto requestDto);
    void createUser(CreateUserRequestDto user);

    void getUserResponseDtoBuilder(List<Users> queryResult, List<GetUsersResponseDto> listGetUserResponse);
}
