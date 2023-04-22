package com.thepan.thepandatingapiserver.domain.user.entity

import com.thepan.thepandatingapiserver.domain.base.BaseEntity
import com.thepan.thepandatingapiserver.domain.user.entity.enum.Sex
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "member")
class Member(
    /**
     * ✅ unique 설정
     * 인덱스가 형성되고, 중복을 허용하지 않는 제약조건이 추가됨
     */
    @Column(nullable = false, length = 30, unique = true)
    var email: String,
    var password: String?,
    @Column(nullable = false, length = 20, unique = true)
    var nickname: String,
    @Column(nullable = false, length = 10)
    var username: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var sex: Sex
) : BaseEntity()
