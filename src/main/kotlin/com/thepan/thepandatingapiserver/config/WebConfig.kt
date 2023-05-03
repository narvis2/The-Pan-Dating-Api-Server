package com.thepan.thepandatingapiserver.config

import com.thepan.thepandatingapiserver.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author choi young-jun
 * ✅ TokenValidationInterceptor 를 Spring 에서 사용할 수 있도록 등록
 *
 * @Configuration 👉 이 클래스가 Spring 에서 사용하는  설정을 담은 Bean 클래스임을 나타냄
 */
@Configuration
class WebConfig @Autowired constructor(
    private val tokenValidationInterceptor: TokenValidationInterceptor
) : WebMvcConfigurer {
    
    // ✅ Interceptor 등록, /api/** 하위의 모든 API 는 해당 Interceptor 가 동작함
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(tokenValidationInterceptor)
            .addPathPatterns("/api/**")
    }
    
    // 🏃‍...TODO:: Resource Directory 등록
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)
    }
}