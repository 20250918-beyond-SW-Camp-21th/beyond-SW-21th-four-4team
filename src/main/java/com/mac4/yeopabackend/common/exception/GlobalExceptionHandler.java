package com.mac4.yeopabackend.common.exception;

import com.mac4.yeopabackend.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    // JSON 파싱/바인딩 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .status(ErrorCode.INVALID_INPUT.getStatus().value())
                .code(ErrorCode.INVALID_INPUT.getCode())
                .message("요청 본문(JSON) 형식이 올바르지 않습니다.")
                .build();

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(error));
    }

    // 동시성 제어
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("DataIntegrityViolationException 발생: ", ex);

        // DB/드라이버마다 메시지 다르니 MostSpecificCause 우선 사용
        String msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        msg = (msg == null) ? "" : msg.toLowerCase();

        if (msg.contains("duplicate") && msg.contains("email")) {
            ErrorCode errorCode = ErrorCode.USER_EMAIL_DUPLICATED;
            return ResponseEntity.status(errorCode.getStatus()).body(ApiResponse.fail(errorCode));
        }

        // 그 외 무결성 위반은 일단 공통 400 또는 500으로 처리 (팀 컨벤션 선택)
        ErrorCode errorCode = ErrorCode.INVALID_INPUT;
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode));
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