package com.thepan.thepandatingapiserver.domain.auth.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

/**
 * @author choi young-jun
 * ✅ Token, Refresh Token 을 만드는 Singleton Class
 *
 * 📌 하는 일이 고정적이고, 다른 코드에 영향을 주지 않기 때문에 Resource 낭비를 줄이고자 Singleton Pattern 적용
 */
object JWTUtils {
    // 발급자
    private const val ISSUER = "ThePanDating"
    
    // 제목
    private const val SUBJECT = "AUTH"
    
    // 일반 Token 만료 시간
    private const val EXPIRE_TIME = 60L * 60 * 2 * 1000 // 2시간
    
    // Refresh Token 만료 시간
    private const val REFRESH_EXPIRE_TIME = 60L * 60 * 24 * 30 * 1000 // 30일
    
    private const val SECRET = "your-secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(SECRET)
    
    private const val REFRESH_SECRET = "your-refresh-secret"
    private val refreshAlgorithm: Algorithm = Algorithm.HMAC256(REFRESH_SECRET)
    
    fun createToken(email: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(algorithm)
    
    fun createRefreshToken(email: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(refreshAlgorithm)
    
    object JWTClaims {
        const val EMAIL = "email"
    }
}