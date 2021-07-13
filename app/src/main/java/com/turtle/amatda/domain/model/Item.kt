package com.turtle.amatda.domain.model

import java.util.*

data class Item(
    val id: Date,
    val name: String = "",
    val count: Int = 1,
    val position_x: Float = 0f,
    val position_y: Float = 0f,
    val width : Int = 250,
    val height : Int = 250,
    val priority: Long = 0,
    val checked : Boolean = false,
    val carrier_id : Long
)