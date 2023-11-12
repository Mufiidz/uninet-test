package id.my.mufidz.mealrecipe.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MealRecipeDao {

    @Upsert(entity = MealEntity::class)
    suspend fun upsertMeal(meal: MealEntity)

    @Query("SELECT * FROM mealentity ORDER BY name")
    suspend fun getMeals() : List<MealEntity>

    @Query("SELECT * FROM mealentity WHERE id =:id")
    suspend fun getMealById(id: String) : MealEntity?

    @Delete(entity = MealEntity::class)
    suspend fun deleteMeal(meal: MealEntity)
}