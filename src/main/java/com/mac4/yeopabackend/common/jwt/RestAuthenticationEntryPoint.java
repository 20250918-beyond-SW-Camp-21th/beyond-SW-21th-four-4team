package com.mac4.yeopabackend.common.jwt;

import com.mac4.yeopabackend.common.exception.BusinessException;
import com.mac4.yeopabackend.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorCode errorCode = ErrorCode.AUTH_UNAUTHORIZED;


        // cause 체인을 끝까지 따라가며 BusinessException 찾기
        Throwable t = authException;
        while (t != null) {
            if (t instanceof BusinessException be) {
                errorCode = be.getErrorCode();
                break;
            }
            t = t.getCause();
        }


        String timestamp = java.time.LocalDateTime.now().toString();
        int status = HttpServletResponse.SC_UNAUTHORIZED;

        String jsonResponse = "{"
                + "\"timestamp\":\"" + timestamp + "\","
                + "\"status\":" + status + ","
                + "\"code\":\"" + errorCode.getCode() + "\","
                + "\"message\":\"" + errorCode.getMessage() + "\""
                + "}";

        response.getWriter().write(jsonResponse);
    }
}