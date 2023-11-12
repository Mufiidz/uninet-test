package id.my.mufidz.mealrecipe.screen.meals

import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.mealrecipe.base.BaseViewModel
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.network.MealRepository
import id.my.mufidz.mealrecipe.data.network.MealsResponse
import id.my.mufidz.mealrecipe.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(private val mealsRepository: MealRepository) :
    BaseViewModel<MealsViewState, MealsAction, MealsResult>(
        MealsViewState()
    ) {
    override fun MealsResult.updateViewState(): MealsViewState = when (this) {
        is MealsResult.MealsDataResult -> result.mapMealResults()
    }

    override fun MealsAction.handleAction(): Flow<MealsResult> = channelFlow {
        when (this@handleAction) {
            is MealsAction.LoadMeals -> {
                updateState { it.copy(state = State.Loading) }
                mealsRepository.getMealsType(query, type).also {
                    send(MealsResult.MealsDataResult(it))
                }
            }
        }
    }

    private fun DataResult<MealsResponse<Meal>>.mapMealResults(): MealsViewState = when (this) {
        is DataResult.Failure -> updateState {
            it.copy(message = error.localizedMessage ?: error.toString(), state = State.Failed)
        }

        is DataResult.Success -> updateState {
            it.copy(meals = data.datas, state = State.Idle)
        }
    }
}