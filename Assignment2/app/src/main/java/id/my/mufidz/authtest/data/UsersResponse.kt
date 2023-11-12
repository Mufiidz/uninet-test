package id.my.mufidz.authtest.data

import id.my.mufidz.authtest.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val data: List<User> = listOf(),
    val page: Int = 0,
    @SerialName("per_page")
    val perPage: Int = 0,
    val total: Int = 0,
    @SerialName("total_pages")
    val totalPages: Int = 0
)