package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.api.WeatherAPIService
import com.turtle.amatda.data.model.WeatherJson
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val api: WeatherAPIService
) : WeatherRemoteDataSource {

    override fun getWeather(
        nx : String,
        ny : String,
        base_date : String,
        base_time : String
    ): Single<Response<WeatherJson>> {
        return api.getWeather(
            numOfRows = "1000",
            pageNo = "1",
            dataType = "JSON",
            nx = nx,
            ny = ny,
            base_date = base_date,
            base_time = base_time
        )
    }

}