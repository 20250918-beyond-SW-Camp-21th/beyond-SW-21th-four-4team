package com.mac4.yeopabackend.user.service;

import com.mac4.yeopabackend.common.exception.BusinessException;
import com.mac4.yeopabackend.common.exception.ErrorCode;
import com.mac4.yeopabackend.common.jwt.TokenBlacklistStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final TokenBlacklistStore tokenBlacklistStore;

    @Transactional
    public void logout(String authorization) {
        String token = extractToken(authorization);
        tokenBlacklistStore.blacklist(token);
    }

    // accessToken 헤더 제거
    private String extractToken(String authorization){
        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new BusinessException(ErrorCode.AUTH_UNAUTHORIZED);
        }
        return authorization.substring(7);
    }
}
