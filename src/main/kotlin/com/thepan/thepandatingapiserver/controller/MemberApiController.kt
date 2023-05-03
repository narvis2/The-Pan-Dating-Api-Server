package com.thepan.thepandatingapiserver.controller

import com.thepan.thepandatingapiserver.common.ApiResponse
import com.thepan.thepandatingapiserver.domain.auth.signup.dto.SignUpRequest
import com.thepan.thepandatingapiserver.domain.auth.signup.service.SignUpService
import com.thepan.thepandatingapiserver.domain.user.dto.MemberInfoResponse
import com.thepan.thepandatingapiserver.domain.user.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/member")
class MemberApiController @Autowired constructor(
    private val signUpService: SignUpService,
    private val memberService: MemberService
) {
    
    @PostMapping("/signup")
    fun signUp(
        @Valid
        @RequestBody
        signUpRequest: SignUpRequest
    ): ApiResponse<Unit> {
        signUpService.signUp(signUpRequest)
        
        return ApiResponse.success()
    }
    
    @GetMapping("/info")
    fun memberInfo(): ApiResponse<MemberInfoResponse> {
        return ApiResponse.success(memberService.getMyMemberInfo())
    }
}