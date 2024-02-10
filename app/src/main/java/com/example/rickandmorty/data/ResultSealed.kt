package com.example.rickandmorty.data

sealed class ResultSealed<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null): ResultSealed<T>(data)
    class Success<T>(data: T?): ResultSealed<T>(data)
    class Error<T>(message: String, data: T? = null): ResultSealed<T>(data, message)
}
