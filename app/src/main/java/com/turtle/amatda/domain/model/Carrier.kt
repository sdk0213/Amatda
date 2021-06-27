package com.turtle.amatda.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Carrier(
    val id: Long = 0,
    val name: String,
    val date: Date,
    val type: String,
    val size: String,
) : Parcelable