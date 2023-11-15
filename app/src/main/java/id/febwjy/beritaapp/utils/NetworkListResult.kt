package id.febwjy.beritaapp.utils

/**
 * Created by Febby Wijaya on 14/11/2023.
 */
sealed class NetworkListResult <out T : Any, out U : Any> {
    data class Success <T: Any>(val data : T) : NetworkListResult<T, Nothing>()
    data class Error <U : Any>(val rawResponse: U) : NetworkListResult<Nothing, U>()
}
