package com.example.mobifillatask.utils

sealed class ResourceApp<T> (
    val data: T? = null,
    val msg : String ? = null
) {

    class Success<T>(data: T) : ResourceApp<T>(data)
    class Error<T>(msg:String, data: T? = null) : ResourceApp<T>(data, msg)
    class Loading<T> : ResourceApp<T>()

}