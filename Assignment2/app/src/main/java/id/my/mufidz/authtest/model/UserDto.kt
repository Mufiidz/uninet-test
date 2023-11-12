package id.my.mufidz.authtest.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String, val password: String
)
