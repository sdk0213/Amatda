package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.DomainLocation
import io.reactivex.Flowable

interface LocationRepository {

    fun getLocation(): Flowable<DomainLocation>

}