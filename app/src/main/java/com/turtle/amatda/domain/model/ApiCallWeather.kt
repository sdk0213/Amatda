package com.turtle.amatda.domain.model

data class ApiCallWeather(
    val nx: String,
    val ny: String,
    val base_date: String,
    val base_time: ApiCallBaseTime
)

// 해당 Base Time 으로 호출하여야한다.
enum class ApiCallBaseTime(
    val standardTimeApiUpdate: Int, // Api 업데이트 되는 시간 (분)
    val standardTimeApiCall: String //  Api 콜시 값으로 요청 해야하는시간
    // ex ) 0200 시에 기준으로 api 요청시에는 0210 이후에 요청해야한다.
) {
    BaseTime1(130, "0200"),
    BaseTime2(310, "0500"),
    BaseTime3(490, "0800"),
    BaseTime4(670, "1100"),
    BaseTime5(850, "1400"),
    BaseTime6(1030, "1700"),
    BaseTime7(1210, "2000"),
    BaseTime8(1390, "2300")
}