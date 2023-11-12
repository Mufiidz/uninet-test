package id.my.mufidz.mealrecipe.model


import android.os.Parcelable
import id.my.mufidz.mealrecipe.data.local.MealEntity
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import timber.log.Timber

@Serializable
@Parcelize
data class Meal(
    @SerialName("dateModified") val dateModified: String? = "",
    @SerialName("idMeal") val id: String = "",
    @SerialName("strArea") val area: String = "-",
    @SerialName("strCategory") val category: String = "",
    @SerialName("strCreativeCommonsConfirmed") val creativeCommonsConfirmed: String? = "",
    @SerialName("strDrinkAlternate") val drinkAlternate: String? = "",
    @SerialName("strImageSource") val imageSource: String = "",
    @SerialName("strIngredient1") val ingredient1: String = "",
    @SerialName("strIngredient10") val ingredient10: String = "",
    @SerialName("strIngredient11") val ingredient11: String = "",
    @SerialName("strIngredient12") val ingredient12: String = "",
    @SerialName("strIngredient13") val ingredient13: String = "",
    @SerialName("strIngredient14") val ingredient14: String = "",
    @SerialName("strIngredient15") val ingredient15: String = "",
    @SerialName("strIngredient16") val ingredient16: String = "",
    @SerialName("strIngredient17") val ingredient17: String = "",
    @SerialName("strIngredient18") val ingredient18: String = "",
    @SerialName("strIngredient19") val ingredient19: String = "",
    @SerialName("strIngredient2") val ingredient2: String = "",
    @SerialName("strIngredient20") val ingredient20: String = "",
    @SerialName("strIngredient3") val ingredient3: String = "",
    @SerialName("strIngredient4") val ingredient4: String = "",
    @SerialName("strIngredient5") val ingredient5: String = "",
    @SerialName("strIngredient6") val ingredient6: String = "",
    @SerialName("strIngredient7") val ingredient7: String = "",
    @SerialName("strIngredient8") val ingredient8: String = "",
    @SerialName("strIngredient9") val ingredient9: String = "",
    @SerialName("strInstructions") val instructions: String = "",
    @SerialName("strMeal") val name: String = "-",
    @SerialName("strMealThumb") val image: String = "",
    @SerialName("strMeasure1") val measure1: String = "",
    @SerialName("strMeasure10") val measure10: String = "",
    @SerialName("strMeasure11") val measure11: String = "",
    @SerialName("strMeasure12") val measure12: String = "",
    @SerialName("strMeasure13") val measure13: String = "",
    @SerialName("strMeasure14") val measure14: String = "",
    @SerialName("strMeasure15") val measure15: String = "",
    @SerialName("strMeasure16") val measure16: String = "",
    @SerialName("strMeasure17") val measure17: String = "",
    @SerialName("strMeasure18") val measure18: String = "",
    @SerialName("strMeasure19") val measure19: String = "",
    @SerialName("strMeasure2") val measure2: String = "",
    @SerialName("strMeasure20") val measure20: String = "",
    @SerialName("strMeasure3") val measure3: String = "",
    @SerialName("strMeasure4") val measure4: String = "",
    @SerialName("strMeasure5") val measure5: String = "",
    @SerialName("strMeasure6") val measure6: String = "",
    @SerialName("strMeasure7") val measure7: String = "",
    @SerialName("strMeasure8") val measure8: String = "",
    @SerialName("strMeasure9") val measure9: String = "",
    @SerialName("strSource") val source: String = "",
    @SerialName("strTags") val tags: String = "",
    @SerialName("strYoutube") val youtube: String = ""
) : Parcelable {

    @IgnoredOnParcel
    var ingredients: List<Ingredient> = listOf()

    fun defaultIngredients() : MutableList<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()

        for (i in baseIngredients.indices) {
            val ingredient = baseIngredients[i]
            val measure = baseMeasures[i]
            val urlImage = "https://www.themealdb.com/images/ingredients/$ingredient.png"

            if (ingredient.isNotEmpty() && measure.isNotEmpty()) {
                Ingredient(i, ingredient, measure, urlImage).also {
                    ingredients.add(it)
                }
            }
        }
        return ingredients
    }

    @IgnoredOnParcel
    val baseIngredients = listOf(
        ingredient1,
        ingredient2,
        ingredient3,
        ingredient4,
        ingredient5,
        ingredient6,
        ingredient7,
        ingredient8,
        ingredient9,
        ingredient10,
        ingredient11,
        ingredient12,
        ingredient13,
        ingredient14,
        ingredient15,
        ingredient16,
        ingredient17,
        ingredient18,
        ingredient19,
        ingredient20,
    )

    @IgnoredOnParcel
    val baseMeasures = listOf(
        measure1,
        measure2,
        measure3,
        measure4,
        measure5,
        measure6,
        measure7,
        measure8,
        measure9,
        measure10,
        measure11,
        measure12,
        measure13,
        measure14,
        measure15,
        measure16,
        measure17,
        measure18,
        measure19,
        measure20,
    )

    fun toMealEntity(): MealEntity = MealEntity(
        id, name, area, image, instructions, youtube, ingredients
    )

}