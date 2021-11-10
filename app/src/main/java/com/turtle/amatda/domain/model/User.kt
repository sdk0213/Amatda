package com.turtle.amatda.domain.model

data class User (
    val id: String = "", // 식별자
    val email: String = "", // 이메일
    val password: String = "", // 패스워드
    val nickName: String = "", // 닉네임
    val photo: String = "", // 프로필이미지
    val exp: Long = 0 // 경험치
)