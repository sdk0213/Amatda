package com.turtle.amatda.data.api

import com.turtle.amatda.data.model.WeatherResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {

    /* 날씨 검색
        *  BASE URL : http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/
        *  지역 날씨 검색 API : /api/weather
        **/
    //    @Header("appKey")String appKey
    @GET("getVilageFcst?serviceKey=bHvtUBnc5PC6pVGM1KoORVYJyXk%2FrLaXVUSH2zrUd%2F1EMxZ%2FMVB1yLkq1ElV5hiB%2Fs8gOD1%2BgInE6ktYkkTRnw%3D%3D")
    fun getWeather(
        // todo: 서비스 키 테스트 필요하다.
//        @Query("serviceKey") serviceKey: String = URLDecoder.decode(ApiClient.WEATHER_API_KEY, "UTF-8"),
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("pageNo") pageNo: String = "1",
        @Query("dataType") dataType: String = "JSON",
        @Query("base_date") base_date: String = "20210824", // 호출하는 시각의 날짜 즉, (반드시 지금 기준)
        @Query("base_time") base_time: String = "0500", // 호출하는 시각의 시간 지금 기준으로 전 것으로 최신화
        @Query("nx") nx: String = "55",
        @Query("ny") ny: String = "127"
    ) : Single<Response<WeatherResponse>>

}