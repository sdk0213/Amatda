package com.turtle.amatda.data.model

// todo(210824) : 파싱문제
//       공공 데이터 포털 실패시 오는 XML 데이터
//       - 정상수신 : Json,
//       - 실패시 : XML
//       위와 같이 다른 타입으로 올경우 이를 동시에 파싱할수있는 방법이 있는지 고민 필요
data class OpenAPIResponse (
    val OpenAPI_ServiceResponse: OpenAPIServiceResponse
)

data class OpenAPIServiceResponse(
    val cmmMsgHeader: CmmMsgHeader
)

data class CmmMsgHeader(
    val errMsg: String,
    val returnAuthMsg: String,
    val returnReasonCode: String
)