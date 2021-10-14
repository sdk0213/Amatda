package com.turtle.amatda.domain.model

import com.google.android.gms.maps.model.LatLng

data class MapCameraPosition(
    val latLng: LatLng,
    val zoom: Float
)

data class MapMarker(
    val latLng: LatLng,
    val title: String
)