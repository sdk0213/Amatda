package com.turtle.amatda.data.api

import com.turtle.amatda.data.model.WeatherJson
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLDecoder

interface WeatherAPIService {

    // 날씨 검색 API
    @GET("getVilageFcst?")
    fun getWeather(
        @Query("serviceKey") serviceKey: String = URLDecoder.decode(ApiClient.WEATHER_API_KEY, "UTF-8"),
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String,
        @Query("dataType") dataType: String,
        @Query("base_date") base_date: String, // 호출하는 시각의 날짜 즉, (반드시 지금 기준)
        @Query("base_time") base_time: String, // 호출하는 시각의 시간 지금 기준으로 전 것으로 최신화
        @Query("nx") nx: String,
        @Query("ny") ny: String
    ) : Single<Response<WeatherJson>>

}