package com.thepan.thepandatingapiserver.domain.auth.signup.dto

import com.thepan.thepandatingapiserver.domain.user.entity.Member
import com.thepan.thepandatingapiserver.domain.user.entity.enum.Sex
import org.mindrot.jbcrypt.BCrypt
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpRequest(
    @field:Email(message = "이메일 형식을 맞춰주세요.")
    @field:NotBlank(message = "이메일을 입력해주세요.")
    val email: String,
    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    val password: String,
    @field:NotBlank(message = "사용자 이름을 입력해주세요.")
    @field:Size(min = 2, max = 10, message = "사용자 이름은 2자 이상 20자 이하여야 합니다.")
    @field:Pattern(regexp = "^[가-힣]+$", message = "사용자 이름은 한글만 입력가능합니다.")
    val username: String,
    @field:NotBlank(message = "닉네임을 입력해주세요.")
    @field:Size(min = 2, message = "닉네임이 너무 짧습니다.")
    @field:Pattern(regexp = "^[A-Za-z가-힣]+$", message = "닉네임은 한글 또는 알파벳만 입력해주세요.")
    val nickname: String,
    @field:NotBlank(message = "성별을 입력해주세요.")
    val sex: String,
) {
    companion object {
        /**
         * @author choi young-jun
         *
         * ✅ BCrypt.hashpw() 👉 문자열을 해싱해줌
         * @param password 👉 비밀번호 넣기
         * @param salt 👉 원본 문자열을 찾아내기 힘들도록 임의의 salt 라는 값을 붙여 함께 해싱 처리
         */
        fun toMemberEntity(request: SignUpRequest): Member =
            Member(
                email = request.email,
                password = BCrypt.hashpw(request.password, BCrypt.gensalt()),
                nickname = request.nickname,
                username = request.username,
                sex = Sex.valueOf(request.sex)
            )
    }
}
