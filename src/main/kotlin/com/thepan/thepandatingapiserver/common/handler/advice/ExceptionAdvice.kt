package com.thepan.thepandatingapiserver.common.handler.advice

import com.thepan.thepandatingapiserver.common.ApiResponse
import com.thepan.thepandatingapiserver.common.handler.exception.*
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
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
    
    // ✅ Validation 진행중 오류 발생
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException): ApiResponse<Unit> {
        // 각 Validation 어노테이션 별로 지정해놨던 메시지를 응답
        return ApiResponse.failure(-1003, e.bindingResult.fieldError?.defaultMessage)
    }
    
    /**
     * ✅ Login 👉 email 및 password 오류, 401 응답을 내려줌
     * 📌 보안상의 이유로 email 이 틀렸든 password 가 틀렸든 실패 응답은 하나로 내려줌
     */
    @ExceptionHandler(LoginFailureException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 응답
    fun loginFailureException(e: LoginFailureException): ApiResponse<Unit> {
        return ApiResponse.failure(-1004, "로그인에 실패하였습니다.")
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
    
    // ✅ 요청한 자원을 찾을 수 없다면, 404 응답
    @ExceptionHandler(MemberNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun memberNotFoundException(e: MemberNotFoundException): ApiResponse<Unit> {
        return ApiResponse.failure(-1007, "요청한 회원을 찾을 수 없습니다.")
    }
    
    // ✅ Token 재발행 GrantType 이 없을 경우, 400 응답
    @ExceptionHandler(RefreshTokenNotGrantTypeException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun refreshTokenNotGrantTypeException(e: RefreshTokenNotGrantTypeException): ApiResponse<Unit> {
        return ApiResponse.failure(-1008, "grant_type 이 없습니다.")
    }
}