package id.my.mufidz.mealrecipe.data.network

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.model.Area
import id.my.mufidz.mealrecipe.model.Meal

interface MealApiServices {

    @GET("categories.php")
    suspend fun getCategories() : DataResult<CategoriesResponse>

    @GET("list.php")
    suspend fun getAreas(@Query("a") area: String? = "list") : DataResult<MealsResponse<Area>>

    @GET("search.php")
    suspend fun getSearchByName(@Query("s") name: String?) : DataResult<MealsResponse<Meal>>

    @GET("lookup.php")
    suspend fun getDetailById(@Query("i") id: String?) : DataResult<MealsResponse<Meal>>

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String?) : DataResult<MealsResponse<Meal>>

    @GET("filter.php")
    suspend fun getMealsByArea(@Query("a") area: String?) : DataResult<MealsResponse<Meal>>
}