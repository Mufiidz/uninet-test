package id.my.mufidz.mealrecipe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(ListIngredientConverter::class)
@Database(version = 1, entities = [MealEntity::class])
abstract class MealRecipeDatabase : RoomDatabase() {
    abstract fun mealDao() : MealRecipeDao
}