package com.thepan.thepandatingapiserver.controller

import com.thepan.thepandatingapiserver.common.ApiResponse
import com.thepan.thepandatingapiserver.common.handler.exception.MemberNotFoundException
import com.thepan.thepandatingapiserver.common.handler.exception.RefreshTokenNotGrantTypeException
import com.thepan.thepandatingapiserver.domain.auth.MemberContextHolder
import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInRequest
import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInResponse
import com.thepan.thepandatingapiserver.domain.auth.signin.service.SignInService
import com.thepan.thepandatingapiserver.domain.auth.utils.JWTUtils
import com.thepan.thepandatingapiserver.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class SignInApiController @Autowired constructor(
    private val signInService: SignInService,
    private val memberContextHolder: MemberContextHolder
) {
    
    @PostMapping("/signIn")
    fun signIn(
        @Valid
        @RequestBody
        signInRequest: SignInRequest
    ): ApiResponse<SignInResponse> = ApiResponse.success(signInService.signIn(signInRequest))
    
    @PostMapping("/signIn/refresh_token")
    fun refreshToken(
        @RequestParam("grant_type") grantType: String
    ): ApiResponse<String> {
        if (grantType != TokenValidationInterceptor.GRANT_TYPE_REFRESH) {
            throw RefreshTokenNotGrantTypeException()
        }
        
        return memberContextHolder.email?.let {
            ApiResponse.success(JWTUtils.createToken(it))
        } ?: throw MemberNotFoundException()
    }
}