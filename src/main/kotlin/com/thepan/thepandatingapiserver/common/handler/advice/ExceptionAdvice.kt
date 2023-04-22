package com.thepan.thepandatingapiserver.common.handler.advice

import com.thepan.thepandatingapiserver.common.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
    
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    /**
     * ✅ 의도하지 않은 예외가 발생하면, 로그를 남겨주고 응답
     * @ExceptionHandler 에서 잡아내지 못한 예외가 여기로 옴
     */
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun exception(e: Exception): ApiResponse<Unit> {
        logger.error("의도치 않은 API Error 👉", e)
        return ApiResponse.failure(-1000, "오류가 발생하였습니다.")
    }
    
    // ✅ Validation 진행중 오류 발생,
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponse<Unit> {
        // 각 Validation 어노테이션 별로 지정해놨던 메시지를 응답
        return ApiResponse.failure(-1003, e.bindingResult.fieldError?.defaultMessage)
    }
}