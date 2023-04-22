package com.thepan.thepandatingapiserver.domain.base

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * ✅ 어노테이션
 * @MappedSuperClass 👉JPA Entity 클래스들이 해당 추상 클래스를 상속할 경우 createDate, modifiedDate를 컬럼으로 인식
 * @EntityListeners(AuditingEntityListener.class) 👉해당 클래스에 Auditing 기능을 포함시킴
 * @CreatedDate 👉Entity 가 생성되어 저장될 때 시간이 자동 저장
 * @LastModifiedDate 👉조회한 Entity 의 값을 변경할 때 시간이 자동 저장
 *
 * 📌 참고 👉 https://dico.me/back-end/articles/308/ko
 */
@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null
    
    @LastModifiedDate
    @Column(nullable = false)
    var modifiedAt: LocalDateTime? = null
}