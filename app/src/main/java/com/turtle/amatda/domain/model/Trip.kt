package com.turtle.amatda.domain.model

import java.util.*

data class Trip(
    val id: Long = 0,
    val title: String,
    val subTitle: String,
    val date_start: Date,
    val date_end: Date
)