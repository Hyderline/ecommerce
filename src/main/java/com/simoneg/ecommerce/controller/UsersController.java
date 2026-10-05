package com.simoneg.ecommerce.controller;

import com.simoneg.ecommerce.dto.CreateUserRequestDto;
import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.dto.GetUsersResponseDto;
import com.simoneg.ecommerce.dto.UpdateUserRequestDto;
import com.simoneg.ecommerce.service.UsersServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@RequestMapping("/users")
@RestController
public class UsersController {

    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/search")
    public ResponseEntity<List<GetUsersResponseDto>> getUsers(@Valid @RequestBody GetUsersRequestDto requestDto) {

        List<GetUsersResponseDto> users = usersService.getUsers(requestDto);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        usersService.createUser(requestDto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username,
                                             @AuthenticationPrincipal UserDetails basicAuth) {
        if(Objects.equals(username, basicAuth.getUsername()) || usersService.checkRoleAdmin(basicAuth.getUsername())) {
            usersService.deleteUser(username);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not authorized to delete this user: " + username, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("update")
    public ResponseEntity<String> deleteUser(
            @Valid @RequestBody UpdateUserRequestDto updateRequestDto,
            @AuthenticationPrincipal UserDetails basicAuth) throws AccessDeniedException {
        if(Objects.equals(updateRequestDto.getUsername(), basicAuth.getUsername()) || usersService.checkRoleAdmin(basicAuth.getUsername())) {
            usersService.updateUser(updateRequestDto, basicAuth.getUsername());
            return new ResponseEntity<>("User Updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not authorized to update this user: " + updateRequestDto.getUsername(), HttpStatus.UNAUTHORIZED);
    }

}
