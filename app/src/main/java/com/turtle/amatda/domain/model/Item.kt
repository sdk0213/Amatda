package com.turtle.amatda.domain.model

data class Item(
    val id: Long = 0,
    val name: String,
    val position_x: Float = 0f,
    val position_y: Float = 0f,
    val priority: Long = 0,
    val checked : Boolean = false,
    val carrier_id : Long
)