package com.turtle.amatda.domain.model

data class DomainLocation (
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val address: String
)