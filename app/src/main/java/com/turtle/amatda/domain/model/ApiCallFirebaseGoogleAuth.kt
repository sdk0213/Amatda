package com.turtle.amatda.domain.model

data class ApiCallFirebaseGoogleAuth(
    val tokenId: String = ""
)

data class ApiCallFirebaseEmailAuth(
    val email: String,
    val password: String
)