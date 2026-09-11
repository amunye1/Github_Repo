package com.example.githubrepository.data.data

sealed class DataResult<out T>{
    data object Idle: DataResult<Nothing>()
    data class Success <T>(val data: T): DataResult<T>()
    data class Error(val message: String): DataResult<Nothing>()
    data object Loading: DataResult<Nothing>()

}