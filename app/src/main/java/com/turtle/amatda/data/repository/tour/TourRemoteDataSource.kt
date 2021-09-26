package com.turtle.amatda.data.repository.tour

import com.turtle.amatda.data.api.TourAPIService
import com.turtle.amatda.data.model.AreaXml
import com.turtle.amatda.data.model.TourXml
import com.turtle.amatda.domain.model.AreaCode
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class TourRemoteDataSource @Inject constructor(
    private val api: TourAPIService
) {
    fun getTour(
        areaCode: AreaCode
    ): Single<Response<TourXml>> {
        return api.getTour(
            areaCode = areaCode.areacode ?: "1",
            sigunguCode = areaCode.sigungucode ?: "1"
        )
    }

}