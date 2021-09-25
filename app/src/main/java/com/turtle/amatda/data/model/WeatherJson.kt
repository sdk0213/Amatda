package com.turtle.amatda.data.model

data class WeatherJson(
    val response: WeatherJsonResponse
)

data class WeatherJsonResponse(
    val header: WeatherJsonHeader,
    val body: WeatherJsonBody
)

data class WeatherJsonHeader(
    val resultCode: Int,
    val resultMsg: String
)

data class WeatherJsonBody(
    val dataType: String,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String,
    val items: WeatherJsonItems
)

data class WeatherJsonItems(
    val item: List<WeatherEntity>
)
