package com.thepan.thepandatingapiserver.controller

import com.thepan.thepandatingapiserver.common.ApiResponse
import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInRequest
import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInResponse
import com.thepan.thepandatingapiserver.domain.auth.signin.service.SignInService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1")
class SignInApiController @Autowired constructor(
    private val signInService: SignInService
) {
    
    @PostMapping("/signIn")
    fun signIn(
        @Valid
        @RequestBody
        signInRequest: SignInRequest
    ): ApiResponse<SignInResponse> = ApiResponse.success(signInService.signIn(signInRequest))
}