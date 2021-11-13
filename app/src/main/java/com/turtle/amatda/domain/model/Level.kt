package com.turtle.amatda.domain.model

enum class Level(val rank: String, val level: Long, val exp: Long, val upExp: Long){
    LEVEL_1("노란색 지도",1,100, 110),
    LEVEL_2("초록색 지도",2,210, 120),
    LEVEL_3("파란색 지도",3,330, 130),
    LEVEL_4("빨강색 지도",4,460, 140),
    LEVEL_5("주황색 지도",5,600, 150),
    LEVEL_6("보라색 지도",6,750, 160),
    LEVEL_7("검정색 지도",7,910, 170),
    LEVEL_8("무지개색 지도",8,1080, 1000)
}
