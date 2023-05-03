package com.thepan.thepandatingapiserver.domain.user.service

import com.thepan.thepandatingapiserver.common.handler.exception.MemberNotFoundException
import com.thepan.thepandatingapiserver.domain.auth.MemberContextHolder
import com.thepan.thepandatingapiserver.domain.user.dto.MemberInfoResponse
import com.thepan.thepandatingapiserver.domain.user.repository.MemberRepository
import com.thepan.thepandatingapiserver.domain.utils.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val memberContextHolder: MemberContextHolder
) {
    fun getMyMemberInfo(): MemberInfoResponse {
        return memberContextHolder.id?.let {
            memberRepository.findByIdOrNull(it)?.let { member ->
                ObjectMapper.memberToMemberInfoResponse(member)
            } ?: throw MemberNotFoundException()
        } ?: throw MemberNotFoundException()
    }
}