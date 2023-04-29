package com.thepan.thepandatingapiserver.domain.auth.signin.dto

data class SignInResponse(
    val token: String,
    val refreshToken: String,
    val userName: String,
    val nickName: String,
    val userId: Long,
)
