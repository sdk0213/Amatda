package com.turtle.amatda.data.model

import kotlin.reflect.KClass

data class UserEntity (
    val id: String, // 식별자
    val email: String, // 이메일
    val password: String, // 패스워드
    val nickName: String, // 닉네임
    val photo: String, // 프로필이미지
    val exp: Long // 경험치
)

enum class FirestoreUser(val variable: String, val type: KClass<*>){
    ID("id", String::class),
    EMAIL("email", String::class),
    PASSWORD("password", String::class),
    NICKNAME("nickName", String::class),
    PHOTO("photo", String::class),
    EXP("exp", Long::class)
}