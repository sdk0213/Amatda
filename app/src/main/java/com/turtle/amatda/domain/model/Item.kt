package com.turtle.amatda.domain.model

data class Item(
    val id: Long = 0,
    val name: String,
    val position_x: Int = 0,
    val position_y: Int = 0,
    val priority: Long = 0,
    val checked : Boolean = false,
    val carrier_id : Long
)