package com.turtle.amatda.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Trip(
    val id: Long = 0,
    val title: String = "", // 퇴사 여행
    val course: String = "", // 부산 -> 포항 -> 영덕
    val nightsAndDays: String = "", // 0박 0일
    val date_start: Date = Date(), // 21/09/28
    val date_end: Date = Date(), // 21/10/01
    val rating: Int = 3 // 3점
) : Parcelable