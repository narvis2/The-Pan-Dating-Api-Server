package com.thepan.thepandatingapiserver.domain.user.repository

import com.thepan.thepandatingapiserver.domain.user.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
    fun findByNickname(nickname: String): Member?
    
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}