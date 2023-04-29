package com.thepan.thepandatingapiserver.domain.auth.signup.service

import com.thepan.thepandatingapiserver.common.handler.exception.UserEmailAlreadyExistsException
import com.thepan.thepandatingapiserver.common.handler.exception.UserNicknameAlreadyExistsException
import com.thepan.thepandatingapiserver.domain.auth.signup.dto.SignUpRequest
import com.thepan.thepandatingapiserver.domain.user.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignUpService @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    fun signUp(signUpRequest: SignUpRequest) {
        validateSignUpInfo(signUpRequest)
        memberRepository.save(SignUpRequest.toMemberEntity(signUpRequest))
    }
    
    /**
     * ✅ 이메일이 존재하는지 체크 👉 존재할 경우 UserEmailAlreadyExistsException 발생
     * ✅ 닉네임이 존재하는지 체크 👉 존재할 경우 UserNicknameAlreadyExistsException 발생
     */
    private fun validateSignUpInfo(signUpRequest: SignUpRequest) = with(signUpRequest) {
        if (memberRepository.existsByEmail(email))
            throw UserEmailAlreadyExistsException(email)
        
        if (memberRepository.existsByNickname(nickname))
            throw UserNicknameAlreadyExistsException(nickname)
    }
}