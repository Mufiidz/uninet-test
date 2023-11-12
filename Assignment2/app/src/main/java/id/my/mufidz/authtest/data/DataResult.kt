package id.my.mufidz.authtest.data

sealed class DataResult<out T> {

    data class Success<T>(val data: T) : DataResult<T>()

    data class Failure(val error: Throwable) : DataResult<Nothing>()
}