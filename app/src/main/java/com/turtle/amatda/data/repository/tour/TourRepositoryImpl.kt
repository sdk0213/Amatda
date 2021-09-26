package com.turtle.amatda.data.repository.tour

import com.google.gson.JsonSyntaxException
import com.tickaroo.tikxml.XmlDataException
import com.turtle.amatda.data.mapper.ResponseMapper
import com.turtle.amatda.data.model.AreaXml
import com.turtle.amatda.data.model.TourXml
import com.turtle.amatda.data.util.Resource
import com.turtle.amatda.domain.model.Area
import com.turtle.amatda.domain.model.AreaCode
import com.turtle.amatda.domain.model.Tour
import com.turtle.amatda.domain.repository.AreaRepository
import com.turtle.amatda.domain.repository.TourRepository
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

class TourRepositoryImpl @Inject constructor(
    private val mapper: ResponseMapper<TourXml, List<Tour>>,
    private val tourRemoteDataSource: TourRemoteDataSource
) : TourRepository {

    override fun getTour(
        areaCode: AreaCode
    ): Single<Resource<List<Tour>>> {
        return tourRemoteDataSource.getTour(
            areaCode = areaCode
        )
            .onErrorReturn {
                var code = 1000
                var message = it.message
                if (it is HttpException) {
                    code = it.code()
                    message = it.message()
                }
                if (it is UnknownHostException) {
                    code = 1001
                    message = "서버에 연결할 수 없습니다. 인터넷 연결을 확인해주세요"
                }
                if (it is XmlDataException) {
                    code = 1002
                    message = "파싱 에러 - 잘못된 값을 반환 받았습니다."
                }
                if (it is IllegalStateException) {
                    code = 1003
                    message = "IllegalStateException : 로그 확인 필요"
                }
                val errorJson = JSONObject()
                    .put("message", message)
                return@onErrorReturn Response.error<TourXml>(
                    code,
                    ResponseBody.create(
                        MediaType.parse("application/json"), errorJson.toString()
                    )
                )
            }
            .flatMap {
                if (it.isSuccessful) {
                    Single.just(mapper.responseToResource(it))
                } else {
                    val errorBody =
                        it.errorBody()?.string() ?: JSONObject().put("message", "알 수 없는 에러")
                            .toString()
                    Single.just(
                        Resource.Error(
                            data = null,
                            code = it.code(),
                            message = JSONObject(errorBody).getString("message") // 실패하면 HTTP 실패 메시지 전송
                        )
                    )
                }
            }

    }

}