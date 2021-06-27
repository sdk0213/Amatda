package com.turtle.amatda.domain.model

data class CarrierAndItems (
    val carrier: Carrier,
    val items: List<Item>
)

data class CarrierAndGetHasItemNum (
    val carrier: Carrier,
    val itemSize: Int = 0
)