package id.my.mufidz.mealrecipe.screen.search

import id.my.mufidz.mealrecipe.base.ActionResult
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.ViewAction
import id.my.mufidz.mealrecipe.base.ViewState
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.network.MealsResponse
import id.my.mufidz.mealrecipe.model.Meal

sealed class SearchAction : ViewAction {
    data class SearchMeal(val query: String) : SearchAction()
}

sealed class SearchResult : ActionResult {
    data class MealsResult(val result: DataResult<MealsResponse<Meal>>) : SearchResult()
}

data class SearchViewState(
    override val state: State = State.Idle,
    override val message: String = "",
    val meals: List<Meal> = listOf()
) : ViewState