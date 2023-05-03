package com.thepan.thepandatingapiserver.interceptor

import com.thepan.thepandatingapiserver.domain.auth.MemberContextHolder
import com.thepan.thepandatingapiserver.domain.auth.utils.JWTUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author choi young-jun
 *
 * ✅ 검증된 Token 으루부터 email 을 가져와 MemberContextHolder 에 넣어주는 로직 작성
 *
 * @Component 👉이 클래스가 Spring 의 Bean 이 관리하는 클래스임을 명시,
 *              @Service 와 기술적으론 동일하지만 의미상으로는 Business 로직을 처리하는 클래스가 아니라는 점에서 다름
 *
 * 📌 HandlerInterceptor 를 사용하여 요청을 가로채서 요청 처리 전/후에 할일을 정의할 수 있음
 */
@Component
class TokenValidationInterceptor @Autowired constructor(
    private val memberContextHolder: MemberContextHolder
) : HandlerInterceptor {
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    // ✅ 요청 처리 전에 호출됨 👉 Token 검증 처리
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 들어온 request 의 header 에서 Authorization 가져옴
        val authHeader = request.getHeader(AUTHORIZATION)
        
        if (authHeader.isNullOrBlank()) {
            val pair = request.method to request.servletPath
            
            // ✅ 인증이 필요한 API 인데 불구하고 header 에 JWT Token 이 없을 경우
            if (!DEFAULT_ALLOWED_API_URLS.contains(pair)) {
                response.sendError(401)
                return false
            }
            
            return true
        } else {
            val grantType = request.getParameter(GRANT_TYPE)
            val token = extractToken(authHeader)
            
            return handleToken(grantType, token, response)
        }
    }
    
    // ✅ 요청 처리 후 View 가 랜더링되기 전에 호출됨 👉응답이 끝났으니 사용자 정보 초기화(다른 사용자 요청이 올수도 있기 때문)
    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        memberContextHolder.clear()
    }
    
    private fun handleToken(
        grantType: String?,
        token: String,
        response: HttpServletResponse
    ): Boolean = try {
        val jwt = when (grantType) {
            GRANT_TYPE_REFRESH -> JWTUtils.verifyRefreshToken(token)
            else -> JWTUtils.verifyToken(token)
        }
        
        // JWT 에서 email 추출
        val email = JWTUtils.extractEmail(jwt)
        memberContextHolder.set(email)
        
        true
    } catch (e: Exception) {
        logger.error("JWT Token 검증 실패. token 👉 $token", e)
        response.sendError(401)
        
        false
    }
    
    /**
     * JWT Token 앞에 Bearer 부분 공백으로 바꾸고 trim() 으로 공백 제거
     * 즉, JWT Token 규격상 맨앞에 존재하는 Bearer 부분 제거
     */
    private fun extractToken(token: String) = token.replace(BEARER, "").trim()
    
    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val GRANT_TYPE = "grant_type" // Token 재발행을 요청할 때 사용될 파라미터의 키
        const val GRANT_TYPE_REFRESH = "refresh_token" // Token 재발행을 요청할 때 사용될 파라미터의 값
        
        // ✅ JWT Token 없이 사용할 수 있는 URL
        private val DEFAULT_ALLOWED_API_URLS = listOf(
            "POST" to "/api/v1/member/signup",
            "POST" to "/api/v1/signIn"
        )
    }
}