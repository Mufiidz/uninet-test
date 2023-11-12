package id.my.mufidz.mealrecipe.data.local

import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.model.Meal
import javax.inject.Inject

interface MealLocalRepository {
    suspend fun upsertMeal(meal: Meal): DataResult<String>
    suspend fun getMeals(): DataResult<List<Meal>>
    suspend fun getMealById(id: String) : DataResult<Meal?>
    suspend fun deleteMeal(meal: Meal): DataResult<String>
}

class MealLocalRepositoryImpl @Inject constructor(private val mealRecipeDao: MealRecipeDao) :
    MealLocalRepository {

    override suspend fun upsertMeal(meal: Meal) = try {
        mealRecipeDao.upsertMeal(meal.toMealEntity())
        DataResult.Success("Berhasil menambahkan ${meal.name}")
    } catch (e: Throwable) {
        DataResult.Failure(e)
    }

    override suspend fun getMeals(): DataResult<List<Meal>> = try {
        val meals = mealRecipeDao.getMeals().map { it.toMeal() }
        DataResult.Success(meals)
    } catch (e: Throwable) {
        DataResult.Failure(e)
    }

    override suspend fun getMealById(id: String): DataResult<Meal?> = try {
        val meal = mealRecipeDao.getMealById(id)?.toMeal()
        DataResult.Success(meal)
    } catch (e: Throwable) {
        DataResult.Failure(e)
    }

    override suspend fun deleteMeal(meal: Meal): DataResult<String> = try {
        mealRecipeDao.deleteMeal(meal.toMealEntity())
        DataResult.Success("Berhasil menghapus ${meal.name}")
    } catch (e: Throwable) {
        DataResult.Failure(e)
    }

}