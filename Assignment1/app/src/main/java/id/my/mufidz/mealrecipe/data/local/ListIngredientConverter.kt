package id.my.mufidz.mealrecipe.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import id.my.mufidz.mealrecipe.model.Ingredient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

@ProvidedTypeConverter
class ListIngredientConverter {

    @TypeConverter
    fun fromIngredients(ingredients: List<Ingredient>) : String =
        Json.encodeToString(ingredients)

    @TypeConverter
    fun toIngredients(items: String) : List<Ingredient> = try {
        Json.decodeFromString(items)
    } catch (e: Throwable) {
        Timber.e(e)
        listOf()
    }
}