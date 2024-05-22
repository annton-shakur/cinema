package com.example.cinema.security;

import com.example.cinema.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private static final String CANNOT_FIND_USER_BY_EMAIL_MSG = "Cannot find user by email: ";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(CANNOT_FIND_USER_BY_EMAIL_MSG + email));
    }
}
