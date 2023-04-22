package com.thepan.thepandatingapiserver.common

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL) // ✅ null 값을 가지는 필드는 Json Response 에 포함되지 않도록 설정
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val resultMsg: String? = null,
    val code: Int
) {
    companion object {
        // ✅ 응답은 성공했는데 반환할 데이터가 없는 경우
        fun <T> success(): ApiResponse<T> = ApiResponse(true, resultMsg = "응답 성공", code = 0)
        
        // ✅ 응답 성공, 데이터 포함
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(true, data, resultMsg = "응답 성공", code = 0)
        
        // ✅ 응답 실패
        fun <T> failure(code: Int, resultMsg: String? = null): ApiResponse<T> =
            ApiResponse(false, resultMsg = resultMsg, code = code)
    }
}