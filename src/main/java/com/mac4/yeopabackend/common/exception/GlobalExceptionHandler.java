package com.mac4.yeopabackend.common.exception;

import com.mac4.yeopabackend.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        log.error("BusinessException 발생: Code: {}, Message: {}", ex.getErrorCode().getCode(), ex.getMessage());

        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode));
    }

    // 회원가입 dto 형식 검증 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse(ErrorCode.INVALID_INPUT.getMessage());

        ErrorResponse error = ErrorResponse.builder()
                .status(ErrorCode.INVALID_INPUT.getStatus().value())
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message(message)
                .build();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("예상치 못한 에러 발생: ", ex);

        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode));
    }
}