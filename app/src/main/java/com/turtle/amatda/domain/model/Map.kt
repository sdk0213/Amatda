package com.turtle.amatda.domain.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import kotlinx.parcelize.Parcelize

data class MapCameraPosition(
    val latLng: LatLng,
    val zoom: Float
)

data class MapMarker(
    val latLng: LatLng,
    val title: String
)

@Parcelize
data class PlaceAndTrip(
    val place: Place,
    val trip: Trip,
) : Parcelable