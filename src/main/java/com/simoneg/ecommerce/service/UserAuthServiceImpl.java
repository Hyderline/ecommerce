package com.simoneg.ecommerce.service;

import com.simoneg.ecommerce.model.Users;
import com.simoneg.ecommerce.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserDetailsService {

    private final UsersRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword_hash())
                .roles(user.getRole().getRoleName())
                .build();
    }
}
