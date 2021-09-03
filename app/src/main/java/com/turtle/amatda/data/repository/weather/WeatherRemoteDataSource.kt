package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.Response

interface WeatherRemoteDataSource {
    fun getWeather(nx : String,
                   ny : String,
                   base_date : String,
                   base_time : String
    ): Single<Response<WeatherResponse>>
}