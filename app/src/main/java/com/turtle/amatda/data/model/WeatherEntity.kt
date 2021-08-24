package com.turtle.amatda.data.model

data class WeatherEntity (
    val baseDate : String,
    val baseTime : String,
    val category : String,
    val fcstDate : String,
    val fcstTime : String,
    val fcstValue : String
)