package com.anggitprayogo.core.util.state

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
sealed class ResultState<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultState<T>()
    data class Error(val error: String?, val statusCode: Int) : ResultState<Nothing>()
    object NetworkError : ResultState<Nothing>()
}