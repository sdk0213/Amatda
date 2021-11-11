package com.turtle.amatda.domain.model

enum class Level(val rank: String, val level: Long, val exp: Long, val upExp: Long){
    LEVEL_1("맨 발",1,100, 100),
    LEVEL_2("짚신",2,210, 110),
    LEVEL_3("슬리퍼",3,330, 120),
    LEVEL_4("샌들",4,460, 130),
    LEVEL_5("운동화",5,600, 140),
    LEVEL_6("워킹화",6,750, 150),
    LEVEL_7("런닝화",7,910, 160),
    LEVEL_8("롤러브레이드",8,1080, 170),
    LEVEL_9("파워브레이드",9,1260, 180),
    LEVEL_10("다이아브레이드",10,1450, 190),
    LEVEL_99("자동차",11,100000000, 99998550)
}
