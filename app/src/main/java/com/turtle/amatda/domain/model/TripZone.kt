package com.turtle.amatda.domain.model

import android.os.Parcelable
import com.turtle.amatda.presentation.utilities.LatXLngY
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class TripZone(
    val id: Long = 0,
    val area: String = "", // 퇴사 여행
    val lat: String,
    val lon: String,
    val zoneType: ZoneType
) : Parcelable

enum class ZoneType(val zone: String) {
    Rest("숙소"),
    Tour("관광지")
}
