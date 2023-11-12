package id.my.mufidz.mealrecipe.screen.detail

import id.my.mufidz.mealrecipe.base.ActionResult
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.ViewAction
import id.my.mufidz.mealrecipe.base.ViewState
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.network.MealsResponse
import id.my.mufidz.mealrecipe.model.Meal

sealed class DetailAction : ViewAction {
    data class GetDetail(val id: String) : DetailAction()
    data class SaveMeal(val meal: Meal) : DetailAction()
    data class DeleteMeal(val meal: Meal) : DetailAction()
}

sealed class DetailResult : ActionResult {
    data class DetailMeal(val result: DataResult<Meal?>) : DetailResult()
    data class SaveMeal(val result: DataResult<String>, val isSaved: Boolean) : DetailResult()
}

data class DetailViewState(
    override val state: State = State.Idle,
    override val message: String = "",
    val meal: Meal? = null,
    val isSaved: Boolean = false
) : ViewState