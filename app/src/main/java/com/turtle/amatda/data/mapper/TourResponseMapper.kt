package com.turtle.amatda.data.mapper

import com.turtle.amatda.data.model.TourXml
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.presentation.utilities.NORMAL_SERVICE
import retrofit2.Response
import javax.inject.Inject

open class TourResponseMapper @Inject constructor() :
    ResponseMapper<TourXml, List<Tour>> {

    override fun responseToResource(response: Response<TourXml>): Resource<List<Tour>> {
        // ResultCode 가 정상(0) 이 아니라면
        if (response.body()?.header?.resultCode != NORMAL_SERVICE) {
            // 에러로 판단
            return Resource.Error(
                data = null,
                code = response.body()?.header?.resultCode,
                message = response.body()?.header?.resultMsg
            )
        }

        val tourList = arrayListOf<Tour>()

        response.body()?.let { result ->
            result.body.items.item?.forEach {
                tourList.add(
                    Tour(
                        addr1 = it.addr1 ?: "",
                        areacode = it.areacode ?: "",
                        cat1 = it.cat1 ?: "",
                        cat2 = it.cat2 ?: "",
                        cat3 = it.cat3 ?: "",
                        contentid = it.contentid ?: "",
                        createdtime = it.createdtime ?: "",
                        mapx = it.mapx ?: "",
                        mapy = it.mapy ?: "",
                        mlevel = it.mlevel ?: "",
                        modifiedtime = it.modifiedtime ?: "",
                        readcount = it.readcount ?: "",
                        sigungucode = it.sigungucode ?: "",
                        title = it.title ?: "",
                        zipcode = it.zipcode ?: "",
                    )
                )
            }
            return Resource.Success(
                data = tourList,
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