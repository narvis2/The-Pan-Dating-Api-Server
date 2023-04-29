package com.thepan.thepandatingapiserver.domain.auth.signin.service

import com.thepan.thepandatingapiserver.common.handler.exception.LoginFailureException
import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInRequest
import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInResponse
import com.thepan.thepandatingapiserver.domain.user.entity.Member
import com.thepan.thepandatingapiserver.domain.user.repository.MemberRepository
import com.thepan.thepandatingapiserver.domain.utils.ObjectMapper
import org.mindrot.jbcrypt.BCrypt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SignInService @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    fun signIn(signInRequest: SignInRequest): SignInResponse {
        val member = memberRepository.findByEmail(signInRequest.email) ?: throw LoginFailureException()
        
        if (isNotValidPassword(signInRequest.password, member.password ?: "")) {
            throw LoginFailureException()
        }
        
        return responseWithTokens(member)
    }
    
    private fun isNotValidPassword(
        password: String,
        hashedPassword: String
    ): Boolean = BCrypt.checkpw(password, hashedPassword).not()
    
    private fun responseWithTokens(member: Member): SignInResponse = member.id?.let { memberId ->
        ObjectMapper.memberToSignInResponse(member, memberId)
    } ?: throw IllegalStateException("user.id 없음")
}