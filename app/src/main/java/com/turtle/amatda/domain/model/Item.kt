package com.turtle.amatda.domain.model

import java.util.*

data class Item(
    val id: Date,
    val name: String = "",
    val count: Int = 1,
    val color: Long = 0xFFFFFFFF,
    val position_x: Float = 0f,
    val position_y: Float = 0f,
    val width : Int = 250,
    val height : Int = 250,
    val priority: Long = 0,
    val checked : Boolean = false,
    val item_place: Int = 1,
    val carrier_id : Long
)