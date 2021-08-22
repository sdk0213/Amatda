package com.turtle.amatda.domain.model

data class Weather (
    val baseDate : String,
    val baseTime : String,
    val category : String,
    val fcstDate : String,
    val fcstTime : String,
    val fcstValue : String
)