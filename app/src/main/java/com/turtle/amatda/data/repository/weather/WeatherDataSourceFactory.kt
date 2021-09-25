package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.model.WeatherJson
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
    ) : Single<Response<WeatherJson>> {
        return remoteDataSource.getWeather(
            nx = nx,
            ny = ny,
            base_date = base_date,
            base_time = base_time
        )
    }
}