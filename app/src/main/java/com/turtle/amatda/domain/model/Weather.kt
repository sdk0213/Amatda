package com.turtle.amatda.domain.model

import java.util.*

data class Weather (
    val date : Date,
    val tmp : Int, // 온도
    val pop : Int, // 비올확률
    val sky : Sky, // 하늘상태
    val pty : Pty // 강수형태
)

enum class Sky (val sky: String){
    Fresh("맑음"), // 1
    Overcast("구름많음"), // 3
    Cloudy("흐림") // 4
}

enum class Pty (val pty: String){
    None("없음"), // 0
    Rain("비"), // 1
    RainOrSnow("비/눈"), // 2
    Snow("눈"), // 3
    Shower("소나기") // 4
}
