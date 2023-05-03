package com.thepan.thepandatingapiserver.domain.auth.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
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
    
    // ✅ JWT Token 생성
    fun createToken(email: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(algorithm)
    
    // ✅ JWT Refresh Token 생성
    fun createRefreshToken(email: String): String = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(refreshAlgorithm)
    
    /**
     * ✅ JwT Token 유효성 검증
     *
     * verify 👉 토큰의 유효성을 검증하고 Decoded 된 JWT 객체를 반환, 만일 Token 이 유효하지 않다면 Exception 을 발생시킴
     */
    fun verifyToken(token: String): DecodedJWT = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token)
    
    // ✅ JWT Refresh Token 유효성 검증
    fun verifyRefreshToken(refreshToken: String): DecodedJWT = JWT.require(refreshAlgorithm).withIssuer(ISSUER).build().verify(refreshToken)
    
    // ✅ JWT 에서 Member Entity 의 unique 값인 Email 추출
    fun extractEmail(jwt: DecodedJWT): String = jwt.getClaim(JWTClaims.EMAIL).asString()
    
    object JWTClaims {
        const val EMAIL = "email"
    }
}