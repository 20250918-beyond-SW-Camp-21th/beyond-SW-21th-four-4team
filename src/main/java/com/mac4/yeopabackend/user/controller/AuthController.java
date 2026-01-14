package com.mac4.yeopabackend.user.controller;

import com.mac4.yeopabackend.common.response.ApiResponse;
import com.mac4.yeopabackend.user.dto.request.SignUpRequestDto;
import com.mac4.yeopabackend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        userService.signUp(signUpRequestDto);
        return ApiResponse.success();
    }
}
