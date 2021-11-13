package com.turtle.amatda.presentation.android.view_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TextViewData(
    val text: String
) : Parcelable