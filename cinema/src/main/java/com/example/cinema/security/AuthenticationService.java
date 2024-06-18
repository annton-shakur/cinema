package com.example.cinema.security;

import com.example.cinema.dto.user.UserLoginRequestDto;
import com.example.cinema.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final Logger logger;

    public UserLoginResponseDto authenticate(final UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getUsername(),
                        requestDto.getPassword()
                )
        );
        logger.info("The next user has logged in: {}", requestDto);
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
}
