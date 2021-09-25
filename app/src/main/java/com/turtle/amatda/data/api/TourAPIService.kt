package com.turtle.amatda.data.api

import com.turtle.amatda.data.model.AreaXml
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLDecoder

interface TourAPIService {

    // 지역 코드 검색 API
    @GET("areaCode?")
    fun getArea(
        @Query("serviceKey") serviceKey: String = URLDecoder.decode(ApiClient.TOUR_API_KEY, "UTF-8"),
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("pageNo") pageNo: String = "1",
        @Query("MobileOS") mobileOS: String = "AND",
        @Query("MobileApp") mobileApp: String = "amatda",
        @Query("areaCode") areaCode: String = ""
    ) : Single<Response<AreaXml>>


}