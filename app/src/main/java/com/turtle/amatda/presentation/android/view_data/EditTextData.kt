package com.turtle.amatda.presentation.android.view_data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditTextData(
    val title: String,
    val hint: String,
    val text: String
) : Parcelable