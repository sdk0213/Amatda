package com.turtle.amatda.data.repository.location

import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.turtle.amatda.domain.model.DomainLocation
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LocationRemoteDataSourceImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    private val geocoder: Geocoder
) {

    private val locationSubject = PublishSubject.create<DomainLocation>()

    val locationObservable: Flowable<DomainLocation> = locationSubject
        .toFlowable(BackpressureStrategy.MISSING)
        .doOnSubscribe { startLocationUpdates() }
        .doOnCancel { stopLocationUpdates() }
        .doOnComplete { stopLocationUpdates() }
        .doOnError { stopLocationUpdates() }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.locations?.forEach(::setLocation)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(::setLocation)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun setLocation(location: Location) {
        val address = geocoder.getFromLocation(location.latitude,
            location.longitude, 1)
        locationSubject.onNext(
            DomainLocation(
                location.latitude,
                location.longitude,
                location.accuracy,
                address = geocoder.getFromLocation(location.latitude,
                    location.longitude, 1)[0]?.getAddressLine(0).toString() ?: "현재 위치 알수 없음"
            )
        )
    }

}