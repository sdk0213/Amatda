package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.model.WeatherResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response

interface WeatherRemoteDataSource {
    fun getWeather(): Single<Response<WeatherResponse>>
}