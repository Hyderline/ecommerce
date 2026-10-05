package com.simoneg.ecommerce.service;

import com.simoneg.ecommerce.dto.GetUsersRequestDto;
import com.simoneg.ecommerce.dto.GetUsersResponseDto;
import com.simoneg.ecommerce.dto.CreateUserRequestDto;
import com.simoneg.ecommerce.dto.UpdateUserRequestDto;
import com.simoneg.ecommerce.enumeration.RolesEnum;
import com.simoneg.ecommerce.model.Companies;
import com.simoneg.ecommerce.model.Permissions;
import com.simoneg.ecommerce.model.Users;
import com.simoneg.ecommerce.repositories.CompaniesRepository;
import com.simoneg.ecommerce.repositories.RolesRepository;
import com.simoneg.ecommerce.repositories.UsersRepository;
import com.simoneg.ecommerce.repositories.specification.UsersSpecifications;
import com.simoneg.ecommerce.utils.exceptions.FieldNotAuthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final CompaniesRepository companiesRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<GetUsersResponseDto> getUsers(GetUsersRequestDto requestDto) {

        List<Users> queryResult = usersRepository.findAll(UsersSpecifications.getUserFilters(requestDto));

        List<GetUsersResponseDto> listGetUserResponse = new ArrayList<>();
        getUserResponseDtoBuilder(queryResult, listGetUserResponse);

        return listGetUserResponse;
    }

    @Transactional
    @Override
    public void createUser(CreateUserRequestDto requestDto) {
        Users user = new Users();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword_hash(passwordEncoder.encode(requestDto.getPassword()));

        user.setRole(rolesRepository.findByRoleName(String.valueOf(requestDto.getRoleName()))
                .orElseThrow(() -> new EntityNotFoundException("Role not found")));

        if (requestDto.getCompanyName() != null) {
            user.setCompany(companiesRepository.findByCompanyName(requestDto.getCompanyName())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found")));
        }

        usersRepository.save(user);
    }

    @Override
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

    @Transactional(readOnly = true)
    @Override
    public boolean checkRoleAdmin(String username) {
        Optional<String> role = usersRepository.findRoleByUsername(username);

        return role.stream().anyMatch(s -> s.equals(RolesEnum.ADMIN.getValue()));
    }

    @Transactional
    @Override
    public void deleteUser(String username) {
        usersRepository.deleteByUsername(username);
    }

    @Transactional
    @Override
    public void updateUser(UpdateUserRequestDto updateRequestDto, String apiUsername) throws AccessDeniedException {
        Users user = usersRepository.findByUsername(updateRequestDto
                .getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not Found: " + updateRequestDto.getUsername()));

        user.setUsername(updateRequestDto.getUsername());
        user.setEmail(updateRequestDto.getEmail());
        user.setPassword_hash(passwordEncoder.encode(updateRequestDto.getPassword()));

        if(updateRequestDto.getRoleName() != null) {
            if(checkRoleAdmin(apiUsername)) {
                user.setRole(rolesRepository.findByRoleName(String.valueOf(updateRequestDto.getRoleName()))
                        .orElseThrow(() -> new EntityNotFoundException("Role not found")));
            } else {
                throw new FieldNotAuthorizedException("role");
            }
        }

        if (updateRequestDto.getCompanyName() != null) {
            checkCanEditCompany(apiUsername, user);

            Companies company = companiesRepository.findByCompanyName(updateRequestDto.getCompanyName())
                    .orElseThrow(() -> new EntityNotFoundException("Company not found"));
            user.setCompany(company);
        }

        usersRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public void checkCanEditCompany(String apiUsername, Users targetUser) throws FieldNotAuthorizedException {
        boolean callerIsAdmin = checkRoleAdmin(apiUsername);
        boolean targetIsSeller = RolesEnum.SELLER.getValue().equals(targetUser.getRole().getRoleName());

        if (!callerIsAdmin || !targetIsSeller) {
            throw new FieldNotAuthorizedException("company");
        }
    }

}
