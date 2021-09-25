package com.turtle.amatda.data.repository.area

import com.turtle.amatda.data.api.TourAPIService
import com.turtle.amatda.data.model.AreaXml
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class AreaRemoteDataSource @Inject constructor(
    private val api: TourAPIService
)  {

    fun getArea(areaCode: String): Single<Response<AreaXml>> {
        return api.getArea(
            areaCode = areaCode
        )
    }

}