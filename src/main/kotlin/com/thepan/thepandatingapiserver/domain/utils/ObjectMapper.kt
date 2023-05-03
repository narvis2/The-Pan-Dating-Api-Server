package com.thepan.thepandatingapiserver.domain.utils

import com.thepan.thepandatingapiserver.domain.auth.signin.dto.SignInResponse
import com.thepan.thepandatingapiserver.domain.auth.utils.JWTUtils
import com.thepan.thepandatingapiserver.domain.user.dto.MemberInfoResponse
import com.thepan.thepandatingapiserver.domain.user.entity.Member

object ObjectMapper {
    fun memberToSignInResponse(member: Member, userId: Long): SignInResponse = SignInResponse(
        token = JWTUtils.createToken(member.email),
        refreshToken = JWTUtils.createRefreshToken(member.email),
        userName = member.username,
        userId = userId,
        nickName = member.nickname
    )
    
    fun memberToMemberInfoResponse(member: Member): MemberInfoResponse = MemberInfoResponse(
        id = member.id ?: 0L,
        email = member.email,
        password = member.password ?: "",
        nickname = member.nickname,
        username = member.username,
        sex = member.sex.value
    )
}