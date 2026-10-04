package com.simoneg.ecommerce.service;

import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.dto.GetUsersResponseDto;
import com.simoneg.ecommerce.dto.CreateUserRequestDto;
import com.simoneg.ecommerce.model.Permissions;
import com.simoneg.ecommerce.model.Users;
import com.simoneg.ecommerce.repositories.CompaniesRepository;
import com.simoneg.ecommerce.repositories.RolesRepository;
import com.simoneg.ecommerce.repositories.UsersRepository;
import com.simoneg.ecommerce.repositories.specification.UsersSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final CompaniesRepository companiesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<GetUsersResponseDto> getUsers(GetUsersRequestDto requestDto) {

        List<Users> queryResult = usersRepository.findAll(UsersSpecifications.from(requestDto));

        List<GetUsersResponseDto> listGetUserResponse = new ArrayList<>();
        getUserResponseDtoBuilder(queryResult, listGetUserResponse);

        return listGetUserResponse;
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequestDto requestDto) {
        Users user = new Users();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword_hash(passwordEncoder.encode(requestDto.getPassword()));

        user.setRole(rolesRepository.findByRoleName(requestDto.getRoleName())
                .orElseThrow(() -> new EntityNotFoundException("Ruolo non trovato")));

        if (requestDto.getCompanyName() != null) {
            user.setCompany(companiesRepository.findByCompanyName(requestDto.getCompanyName())
                    .orElseThrow(() -> new EntityNotFoundException("Azienda non trovata")));
        }

        usersRepository.save(user);
    }

    public void getUserResponseDtoBuilder(List<Users> queryResult, List<GetUsersResponseDto> listGetUserResponse) {
        for(Users user : queryResult) {
            GetUsersResponseDto userDto = new GetUsersResponseDto();

            List<String> permissionList = new ArrayList<>();
            for(Permissions permission : user.getRole().getPermissions()) {
                permissionList.add(permission.getPermissionName());
            }

            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setUpdatedAt(user.getUpdatedAt());
            userDto.setRoleName(user.getRole().getRoleName());
            userDto.setPermissions(permissionList);
            if(user.getCompany() != null) {
                userDto.setCompanyName(user.getCompany().getCompanyName());
            }

            listGetUserResponse.add(userDto);
        }
    }

}
