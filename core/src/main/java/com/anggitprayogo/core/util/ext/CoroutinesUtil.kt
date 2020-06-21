package com.anggitprayogo.core.util.ext

import com.anggitprayogo.core.util.state.ResultState
import com.bumptech.glide.load.HttpException
import java.io.IOException
import java.net.ConnectException

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
enum class StatusCode(val value: Int) {
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503)
}

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> ResultState<T>): ResultState<T> {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ResultState.NetworkError
            is HttpException -> {
                val code = throwable.statusCode
                val errorResponse = throwable.message
                return ResultState.Error(errorResponse, code)
            }
            is ConnectException -> ResultState.NetworkError
            else -> ResultState.Error(null, 500)
        }
    }
}

//fun <T : Any> safeApiErrorHandling(response: Response<T>): ResultState<T> {
//    val errorResponse = response.message()
//    return when (response.code()) {
//        else -> {
//            val errorMessageFromApi = decodeErroMessage(response.errorBody(), "message")
//
//            when {
//                errorMessageFromApi.isNullOrEmpty().not() -> {
//                    ResultState.GeneralError(response.code(), errorMessageFromApi, response.errorBody())
//                }
//                else -> {
//                    ResultState.GeneralError(response.code(), errorResponse, response.errorBody())
//                }
//            }
//        }
//    }
//}
//
//private fun decodeErroMessage(body: ResponseBody?, key: String): String? {
//    return JSONObject(body?.string()).getString(key)
//}