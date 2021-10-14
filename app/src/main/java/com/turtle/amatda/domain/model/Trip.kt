package com.turtle.amatda.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Trip(
    val id: Long = 0,
    val title: String = "", // 퇴사 여행
    val type: TripConcept = TripConcept.NORMAL,
    val zoneList: List<TripZone> = arrayListOf(TripZone()), // 여행 지역
    val nightsAndDays: String = "", // 0박 0일
    val date_start: Date = Date(), // 21/09/28
    val date_end: Date = Date(), // 21/10/01
    val rating: Int = 3 // 3점
) : Parcelable

enum class TripConcept(val concept: String) {
    NORMAL("평범한"),
    FUNNY("놀고싶은"),
    ACTIVITY("활동적인(레저)"),
    REST("쉬고싶은"),
    LOVELY("사랑하는 사람과 함께하는"),
    HONEYMOON("신혼을 즐기는"),
    CULTURE("역사와 문화를 살펴보는"),
    STUDY("공부하는"),
    WORK("일하는"),
    FOOD("맛있는 음식과 함께하는"),
    SELFREFLECTION("나를 돌아보는"),
    REFRESH("충전하는"),
    BOOK("책과 함께하는"),
    MUSIC("음악과 함께하는"),
    CHALLENGER("새로운것을 도전하는"),
    FRIENDSHIP("우정을 다지는")
}