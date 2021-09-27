package com.turtle.amatda.data.repository.tour

import com.turtle.amatda.data.api.TourAPIService
import com.turtle.amatda.data.model.TourXml
import com.turtle.amatda.domain.model.TourCode
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class TourRemoteDataSource @Inject constructor(
    private val api: TourAPIService
) {
    fun getTour(
        tourCode: TourCode
    ): Single<Response<TourXml>> {
        return api.getTour(
            contentTypeId = tourCode.contentTypeId ?: "12",
            areaCode = tourCode.areacode ?: "1",
            sigunguCode = tourCode.sigungucode ?: "1"
        )
    }

}