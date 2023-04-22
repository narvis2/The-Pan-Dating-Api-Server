package com.thepan.thepandatingapiserver.domain.user.repository

import com.thepan.thepandatingapiserver.domain.user.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

}