package id.my.mufidz.authtest.data

import kotlinx.serialization.Serializable

sealed class LoginResponse {
    @Serializable
    data class Success(val token: String) : LoginResponse()
    @Serializable
    data class Failed(val error: String) : LoginResponse()
}