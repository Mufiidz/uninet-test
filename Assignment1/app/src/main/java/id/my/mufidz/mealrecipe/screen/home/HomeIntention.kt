package id.my.mufidz.mealrecipe.screen.home

import id.my.mufidz.mealrecipe.base.ActionResult
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.ViewAction
import id.my.mufidz.mealrecipe.base.ViewState
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.network.CategoriesResponse
import id.my.mufidz.mealrecipe.data.network.MealsResponse
import id.my.mufidz.mealrecipe.model.Area
import id.my.mufidz.mealrecipe.model.Category
import id.my.mufidz.mealrecipe.model.Meal

sealed class HomeAction : ViewAction {
    data object LoadHome : HomeAction()
}

sealed class HomeResult : ActionResult {
    data class CategoriesData(val result: DataResult<CategoriesResponse>) : HomeResult()
    data class AreasData(val result: DataResult<MealsResponse<Area>>) : HomeResult()
    data class SavedRecipe(val result: DataResult<List<Meal>>) : HomeResult()
}

data class HomeViewState(
    override val message: String = "",
    override val state: State = State.Idle,
    val areas: List<Area> = listOf(),
    val categories: List<Category> = listOf(),
    val savedMeals: List<Meal> = listOf()
) : ViewState