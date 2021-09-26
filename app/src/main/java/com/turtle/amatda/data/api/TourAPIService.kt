package com.turtle.amatda.data.api

import com.turtle.amatda.data.model.AreaXml
import com.turtle.amatda.data.model.TourXml
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

    // 지역 코드를 기반으로한 관광지 검색
    @GET("areaBasedList?")
    fun getTour(
        @Query("serviceKey") serviceKey: String = URLDecoder.decode(ApiClient.TOUR_API_KEY, "UTF-8"),
        @Query("pageNo") pageNo: String = "1",
        @Query("numOfRows") numOfRows: String = "1000",
        @Query("MobileApp") mobileApp: String = "amatda",
        @Query("MobileOS") mobileOS: String = "AND",
        @Query("arrange") arrange: String = "A",
        @Query("cat1") cat1: String = "",
        // contentTypeId :
        // 관광지(12), 문화시설(14), 축체/공연/행사(15), 여행코스(25), 레포츠(28), 숙박(32), 쇼핑(38), 음식(39)
        // 한국관광공사_서비스분류코드_v3.2_160623 참고하기 (공공데이터 포털)
        @Query("contentTypeId") contentTypeId: String = "12",
        @Query("areaCode") areaCode: String,
        @Query("sigunguCode") sigunguCode: String,
        @Query("cat2") cat2: String = "",
        @Query("cat3") cat3: String = "",
        @Query("listYN") listYN: String = "Y",
        @Query("modifiedtime") modifiedtime: String = ""
    ) : Single<Response<TourXml>>

}