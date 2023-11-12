package id.my.mufidz.mealrecipe.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("idCategory")
    val id: String = "",
    @SerialName("strCategory")
    val category: String = "",
    @SerialName("strCategoryDescription")
    val description: String = "",
    @SerialName("strCategoryThumb")
    val image: String = ""
)