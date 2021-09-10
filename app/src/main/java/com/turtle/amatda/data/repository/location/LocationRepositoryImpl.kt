package com.turtle.amatda.data.repository.location

import com.turtle.amatda.domain.model.DomainLocation
import com.turtle.amatda.domain.repository.LocationRepository
import io.reactivex.Flowable
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationRemoteDataSourceImpl: LocationRemoteDataSourceImpl
) : LocationRepository {

    override fun getLocation(): Flowable<DomainLocation> {
        return locationRemoteDataSourceImpl
            .locationObservable
    }

}