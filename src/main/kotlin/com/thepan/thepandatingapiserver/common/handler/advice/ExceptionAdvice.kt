package com.thepan.thepandatingapiserver.common.handler.advice

import com.thepan.thepandatingapiserver.common.ApiResponse
import com.thepan.thepandatingapiserver.common.handler.exception.UserEmailAlreadyExistsException
import com.thepan.thepandatingapiserver.common.handler.exception.UserNicknameAlreadyExistsException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

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
//        val message = Objects.requireNonNull<FieldError>(e.bindingResult.fieldErrors.first()).defaultMessage
        // 각 Validation 어노테이션 별로 지정해놨던 메시지를 응답
        return ApiResponse.failure(-1003, e.bindingResult.fieldError?.defaultMessage)
    }
    
    // ✅ 이메일 중복 오류
    @ExceptionHandler(UserEmailAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409 응답
    fun userEmailAlreadyExistsException(e: UserEmailAlreadyExistsException): ApiResponse<Unit> {
        return ApiResponse.failure(-1005, e.message + "은 중복된 이메일 입니다.")
    }
    
    // ✅ nickname 중복 오류
    @ExceptionHandler(UserNicknameAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    fun userNicknameAlreadyExistsException(e: UserNicknameAlreadyExistsException): ApiResponse<Unit> {
        return ApiResponse.failure(-1006, e.message + "은 중복된 닉네임 입니다.")
    }
}