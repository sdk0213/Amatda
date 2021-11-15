package com.turtle.amatda.domain.model

data class CarrierAndGetHasPocketNum(
    val carrier: Carrier,
    val pocketSize: Int = 0
)

data class CarrierAndPocket(
    val carrier: Carrier,
    val pockets: List<Pocket>
)

data class CarrierWithPocketAndItems(
    val carrier: Carrier,
    val pocketAndItems: List<PocketAndItem>
)

data class PocketAndItem(
    val pocket: Pocket,
    val items: List<Item>
)

data class PocketAndItemSize(
    val pocket: Pocket,
    val itemSize: Int = 0
)