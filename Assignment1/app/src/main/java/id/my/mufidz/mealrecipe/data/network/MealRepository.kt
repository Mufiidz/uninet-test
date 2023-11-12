package id.my.mufidz.mealrecipe.data.network

import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.local.MealLocalRepository
import id.my.mufidz.mealrecipe.model.Area
import id.my.mufidz.mealrecipe.model.Meal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class MealType { CATEGORY, AREA, SAVED }

interface MealRepository {
    suspend fun getCategories(): DataResult<CategoriesResponse>
    suspend fun getAreas(): DataResult<MealsResponse<Area>>
    suspend fun searchByName(name: String): DataResult<MealsResponse<Meal>>
    suspend fun detailById(id: String): DataResult<Meal?>
    suspend fun getMealsType(query: String, type: MealType): DataResult<MealsResponse<Meal>>
}

class MealRepositoryImpl @Inject constructor(
    private val mealApiServices: MealApiServices,
    private val mealLocalRepository: MealLocalRepository
) : MealRepository {

    override suspend fun getCategories(): DataResult<CategoriesResponse> =
        withContext(Dispatchers.IO) {
            mealApiServices.getCategories()
        }

    override suspend fun getAreas(): DataResult<MealsResponse<Area>> = withContext(Dispatchers.IO) {
        mealApiServices.getAreas()
    }

    override suspend fun searchByName(name: String): DataResult<MealsResponse<Meal>> =
        withContext(Dispatchers.IO) {
            mealApiServices.getSearchByName(name)
        }

    override suspend fun detailById(id: String): DataResult<Meal?> = withContext(Dispatchers.IO) {
        when (val response = mealApiServices.getDetailById(id)) {
            is DataResult.Failure -> response
            is DataResult.Success -> {
                val meal = response.data.datas.firstOrNull()
                meal?.apply { ingredients = defaultIngredients() }
                DataResult.Success(meal)
            }
        }
    }

    override suspend fun getMealsType(
        query: String, type: MealType
    ): DataResult<MealsResponse<Meal>> = withContext(Dispatchers.IO) {
        when (type) {
            MealType.CATEGORY -> mealApiServices.getMealsByCategory(query)
            MealType.AREA -> mealApiServices.getMealsByArea(query)
            MealType.SAVED -> {
                when (val result = mealLocalRepository.getMeals()) {
                    is DataResult.Failure -> result
                    is DataResult.Success -> DataResult.Success(MealsResponse(result.data))
                }
            }
        }
    }

}

