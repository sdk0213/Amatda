package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.AreaXml
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Area
import com.turtle.amatda.presentation.utilities.NORMAL_SERVICE
import retrofit2.Response
import javax.inject.Inject

open class AreaResponseMapper @Inject constructor() :
    ResponseMapper<AreaXml, List<Area>> {

    override fun responseToResource(response: Response<AreaXml>): Resource<List<Area>> {
        // ResultCode 가 정상(0) 이 아니라면
        if (response.body()?.header?.resultCode != NORMAL_SERVICE) {
            // 에러로 판단
            return Resource.Error(
                data = null,
                code = response.body()?.header?.resultCode,
                message = response.body()?.header?.resultMsg
            )
        }

        val areaList = arrayListOf<Area>()

        response.body()?.let { result ->
            result.body.items.item?.forEach {
                areaList.add(
                    Area(
                        code = it.code ?: "",
                        name = it.name ?: "",
                        rnum = it.rnum ?: ""
                    )
                )
            }
            return Resource.Success(
                data = areaList,
                code = result.header.resultCode,
                message = result.header.resultMsg
            )
        }

        return Resource.Error(
            data = null,
            message = response.message() // 실패하면 HTTP 실패 메시지 전송
        )
    }
}