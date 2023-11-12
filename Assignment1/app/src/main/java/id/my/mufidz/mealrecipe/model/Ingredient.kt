package id.my.mufidz.mealrecipe.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Ingredient(
    val id: Int,
    val name: String,
    val measure: String,
    val image: String
) : Parcelable
