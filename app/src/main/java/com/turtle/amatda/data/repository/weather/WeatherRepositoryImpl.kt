package com.turtle.amatda.data.repository.weather

import com.turtle.amatda.data.mapper.ResponseMapper
import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.repository.WeatherRepository
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val mapper: ResponseMapper<WeatherResponse>,
    private val factory: WeatherDataSourceFactory
) : WeatherRepository {

    override fun getWeather(): Single<Resource<WeatherResponse>> {
        return factory.getWeather()
            .flatMap {
                Single.just(mapper.responseToMap(it))
            }

    }
}