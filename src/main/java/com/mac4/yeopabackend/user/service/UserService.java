package com.mac4.yeopabackend.user.service;

import com.mac4.yeopabackend.common.exception.BusinessException;
import com.mac4.yeopabackend.common.exception.ErrorCode;
import com.mac4.yeopabackend.common.jwt.JwtTokenProvider;
import com.mac4.yeopabackend.user.domain.User;
import com.mac4.yeopabackend.user.dto.request.SignUpRequestDto;
import com.mac4.yeopabackend.user.dto.response.TokenResponseDto;
import com.mac4.yeopabackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public TokenResponseDto signUp(SignUpRequestDto request){

        if(userRepository.existsByEmail(request.email())){
            throw new BusinessException(ErrorCode.USER_EMAIL_DUPLICATED);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .username(request.username())
                .description(request.description())
                .build();

        userRepository.save(user);

        String accessToken = jwtTokenProvider.createToken(user.getId(), user.getEmail());

        return new TokenResponseDto(accessToken);
    }
}
