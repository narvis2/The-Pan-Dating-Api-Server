package com.thepan.thepandatingapiserver.domain.user.service

import com.thepan.thepandatingapiserver.domain.user.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MemberService @Autowired constructor(
    private val memberRepository: MemberRepository
) {
}