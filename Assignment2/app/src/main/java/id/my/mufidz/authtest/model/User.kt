package id.my.mufidz.authtest.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val avatar: String = "",
    val email: String = "",
    @SerialName("first_name")
    val firstName: String = "",
    val id: Int = 0,
    @SerialName("last_name")
    val lastName: String = ""
) {
    val fullName: String = "$firstName $lastName"
}
