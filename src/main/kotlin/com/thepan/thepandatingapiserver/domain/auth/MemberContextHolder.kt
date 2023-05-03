package com.thepan.thepandatingapiserver.domain.auth

import com.thepan.thepandatingapiserver.domain.user.entity.enum.Sex
import com.thepan.thepandatingapiserver.domain.user.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author choi young-jun
 *
 * ThreadLocal 영역애 Local 변수 할당
 * MemberRepository 로부터 사용자 정보를 읽어 ThreadLocal<MemberHolder> 타입의 프로퍼티에 저장
 *
 * 📌 어디에서든 사용자의 정보를 가져와 Token 의 유효성 검증 및 권한 설정등에 사용
 *
 * ❗️ 주의
 * - 요청이 끝나는 시점에 이 Local 변수를 초기회해야 다른 사용자의 요청이 동일 스레드에서 실행되었을 때
 * 사용자의 정보가 섞이지 않음
 * - 특정 요청을 Multi Thread 로 처리하려고 하는 경우 새 Thread 에서는 기본적으로 사용자 정보에 접근할 수 없음
 */
@Service
class MemberContextHolder @Autowired constructor(
    private val memberRepository: MemberRepository
) {
    private val memberHolder = ThreadLocal.withInitial {
        MemberHolder()
    }
    
    val id: Long?
        get() = memberHolder.get().id
    
    val email: String?
        get() = memberHolder.get().email
    
    val nickname: String?
        get() = memberHolder.get().nickname
    
    val username: String?
        get() = memberHolder.get().username
    
    val sex: Sex?
        get() = memberHolder.get().sex
    
    // 사용자 정보를 저장해주는 함수
    fun set(email: String) = memberRepository.findByEmail(email)?.let { member ->
        this.memberHolder.get().apply {
            this.id = member.id
            this.email = member.email
            this.nickname = member.nickname
            this.username = member.username
            this.sex = member.sex
        }.run(memberHolder::set)
    }
    
    // 사용자 정보를 초기화해주는 함수
    fun clear() {
        memberHolder.remove()
    }
    
    class MemberHolder {
        var id: Long? = null
        var email: String? = null
        var nickname: String? = null
        var username: String? = null
        var sex: Sex? = null
    }
}