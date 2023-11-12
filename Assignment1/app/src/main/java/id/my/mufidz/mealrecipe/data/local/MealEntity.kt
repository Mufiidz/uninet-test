package id.my.mufidz.mealrecipe.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.my.mufidz.mealrecipe.model.Ingredient
import id.my.mufidz.mealrecipe.model.Meal

@Entity
data class MealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val area: String,
    val image: String,
    val recipe: String,
    val youtubeUrl: String,
    val ingredients: List<Ingredient>
) {
    fun toMeal() : Meal = Meal(
        id = id,
        name = name,
        area = area,
        image = image,
        instructions = recipe,
        youtube = youtubeUrl
    ).also { it.ingredients = ingredients }
}