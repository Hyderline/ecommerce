package com.simoneg.ecommerce.controller;

import com.simoneg.ecommerce.dto.CreateUserRequestDto;
import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.dto.GetUsersResponseDto;
import com.simoneg.ecommerce.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UsersController {

    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/search")
    public ResponseEntity<List<GetUsersResponseDto>> getUsers(@RequestBody GetUsersRequestDto requestDto) {

        List<GetUsersResponseDto> users = usersService.getUsers(requestDto);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CreateUserRequestDto requestDto) {
        usersService.createUser(requestDto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

}
