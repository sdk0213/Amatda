package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.api.WeatherAPIService
import com.turtle.amatda.data.model.WeatherResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val api: WeatherAPIService
) : WeatherRemoteDataSource {

    override fun getWeather(): Single<Response<WeatherResponse>> {
        return api.getWeather()
    }

}