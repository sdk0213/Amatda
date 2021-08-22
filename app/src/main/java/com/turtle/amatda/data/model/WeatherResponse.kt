package com.turtle.amatda.data.model

import com.turtle.amatda.domain.model.Weather

data class WeatherResponse(
    val response: Response
)

data class Response(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: Int,
    val resultMsg: String
)

data class Body(
    val dataType: String,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String,
    val items: Items
)

data class Items(
    val item: List<Weather>
)
