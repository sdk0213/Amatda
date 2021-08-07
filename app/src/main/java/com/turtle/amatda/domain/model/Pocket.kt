package com.turtle.amatda.domain.model

import java.util.*

data class Pocket (
    val id: Date,
    val name: String = "큰 주머니",
    val carrier_id: Long
)