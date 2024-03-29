package com.turtle.amatda.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TripZone(
    val id: Long = 0,
    val area: String = "", // 지역명
    val addr: String = "",
    val title: String = "", // 메모
    val date: Date = Date(), // 방문 날짜
    val lat: String = "0", // 위도
    val lon: String = "0", // 경도
    val zoneType: ZoneType = ZoneType.NONE // 존 타입
) : Parcelable

enum class ZoneType(val zone: String) {
    NONE("해당 없음"),
    ACCOMMODATION("숙소"),
    RESTAURANT("음식점"),
    CAFE("카페"),
    MARKET("마트/편의점"),
    TOURISTAREA("관광"),
    LEISUREACTIVITY("레저"),
    FESTIVAL("축제"),
    BUSTERMINAL("버스터미널"),
    STATION("기차역"),
    CAR("차 보관소"),

}

data class TripZoneItem(
    val listItemType: ListItemType,
    val item: Any
)
