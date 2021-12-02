package com.turtle.amatda.presentation.utilities

enum class AmatdaExceptionMessage(val exception: Exception) {
    // 구글 로그인
    EmailVerificationRequired(Exception("EmailNotVerified")),
    // 구글 로그인
    InvalidPassword(Exception("FirebaseAuthInvalidCredentialsException")),
    // 구글 로그인
    InvalidAuthUser(Exception("FirebaseAuthInvalidUserException")),
    // Firebase 유저 인증
    ThereIsNoCurrentUser(Exception("CurrentUser is null"))

}