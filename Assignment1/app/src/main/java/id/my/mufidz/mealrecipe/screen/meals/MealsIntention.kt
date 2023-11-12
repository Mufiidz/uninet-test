package id.my.mufidz.mealrecipe.screen.meals

import id.my.mufidz.mealrecipe.base.ActionResult
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.ViewAction
import id.my.mufidz.mealrecipe.base.ViewState
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.network.MealType
import id.my.mufidz.mealrecipe.data.network.MealsResponse
import id.my.mufidz.mealrecipe.model.Meal

sealed class MealsAction : ViewAction {
    data class LoadMeals(val type: MealType, val query: String) : MealsAction()
}

sealed class MealsResult : ActionResult {
    data class MealsDataResult(val result: DataResult<MealsResponse<Meal>>) : MealsResult()
}

data class MealsViewState(
    override val state: State = State.Idle,
    override val message: String = "",
    val meals: List<Meal> = listOf()
) : ViewState