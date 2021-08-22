package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.WeatherResponse
import com.turtle.amatda.data.util.Resource
import retrofit2.Response
import javax.inject.Inject

open class WeatherResponseMapper @Inject constructor() : ResponseMapper<WeatherResponse> {

    override fun responseToMap(response : Response<WeatherResponse>): Resource<WeatherResponse> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}