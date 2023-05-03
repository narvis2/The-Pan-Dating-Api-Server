package com.thepan.thepandatingapiserver.domain.user.dto
data class MemberInfoResponse(
    val id: Long,
    val email: String,
    val password: String,
    val nickname: String,
    val username: String,
    val sex: String
)
