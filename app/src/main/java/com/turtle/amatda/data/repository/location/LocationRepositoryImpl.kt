package com.turtle.amatda.data.repository.location

import com.turtle.amatda.domain.model.DomainLocation
import com.turtle.amatda.domain.repository.LocationRepository
import io.reactivex.Flowable
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteDataSource: LocationRemoteDataSource
) : LocationRepository {

    override fun getLocation(): Flowable<DomainLocation> {
        return locationRemoteDataSource
            .locationObservable
    }

}