package com.turtle.amatda.data.model

data class WeatherEntity (
    val nx: String,
    val ny: String,
    val baseDate : String,
    val baseTime : String,
    val category : String,
    val fcstDate : String,
    val fcstTime : String,
    val fcstValue : String
)