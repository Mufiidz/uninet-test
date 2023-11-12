package id.my.mufidz.mealrecipe.data.network

import id.my.mufidz.mealrecipe.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsResponse<T>(
    @SerialName("meals") val datas: List<T>
)

@Serializable
data class CategoriesResponse(val categories: List<Category>)
