package id.my.mufidz.mealrecipe.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Area(
    @SerialName("strArea")
    val name: String = ""
)