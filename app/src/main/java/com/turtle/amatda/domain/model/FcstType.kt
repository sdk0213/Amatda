package com.turtle.amatda.domain.model

enum class FcstType(val type: String, val unit: String) {
    POP("강수확률","%"),
    PTY("강수형태","code"),
    PCP("1시간 강수량","mm"),
    REH("습도","%"),
    SNO("1시간 신적설","cm"),
    SKY("하늘상태","code"),
    TMP("1시간 기온","℃"),
    TMN("일 최저기온","℃"),
    TMX("일 최고기온","℃"),
    UUU("풍속(동서성분)","m/s"),
    VVV("풍속(남북성분)","m/s"),
    WAV("파고","M"),
    VEC("풍향","deg"),
    WSD("풍속","m/s")
}