package com.turtle.amatda.domain.model

data class Carrier(
    val id: Long = 0,
    val carrier_name: String,
    val item: List<Item>
)