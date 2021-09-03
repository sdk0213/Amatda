package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.model.WeatherEntity
import com.turtle.amatda.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class WeatherDataSourceFactory @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    fun getWeather(nx : String,
                   ny : String,
                   base_date : String,
                   base_time : String
    ) : Single<Response<WeatherResponse>> {
        return remoteDataSource.getWeather(
            nx = nx,
            ny = ny,
            base_date = base_date,
            base_time = base_time
        )
    }
}