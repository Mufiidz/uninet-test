package id.my.mufidz.mealrecipe.screen.home

import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.mealrecipe.base.BaseViewModel
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.local.MealLocalRepository
import id.my.mufidz.mealrecipe.data.network.CategoriesResponse
import id.my.mufidz.mealrecipe.data.network.MealRepository
import id.my.mufidz.mealrecipe.data.network.MealsResponse
import id.my.mufidz.mealrecipe.model.Area
import id.my.mufidz.mealrecipe.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepository: MealRepository, private val mealLocalRepository: MealLocalRepository
) : BaseViewModel<HomeViewState, HomeAction, HomeResult>(HomeViewState()) {

    override fun HomeResult.updateViewState(): HomeViewState = when (this) {
        is HomeResult.AreasData -> result.mapResult()
        is HomeResult.CategoriesData -> result.mapCategoryResult()
        is HomeResult.SavedRecipe -> result.mapSavedMeals()
    }

    override fun HomeAction.handleAction(): Flow<HomeResult> = channelFlow {
        when (this@handleAction) {
            HomeAction.LoadHome -> {
                updateState { it.copy(state = State.Loading) }
                mealRepository.getAreas().also {
                    send(HomeResult.AreasData(it))
                }
                mealRepository.getCategories().also {
                    send(HomeResult.CategoriesData(it))
                }
                mealLocalRepository.getMeals().also {
                    send(HomeResult.SavedRecipe(it))
                }
            }
        }
    }

    private fun DataResult<CategoriesResponse>.mapCategoryResult(): HomeViewState = when (this) {
        is DataResult.Failure -> updateState {
            it.copy(message = error.localizedMessage ?: error.toString(), state = State.Failed)
        }

        is DataResult.Success -> updateState {
            it.copy(categories = data.categories, state = State.Idle)
        }
    }

    private fun DataResult<MealsResponse<Area>>.mapResult(): HomeViewState = when (this) {
        is DataResult.Failure -> updateState {
            it.copy(message = error.localizedMessage ?: error.toString(), state = State.Failed)
        }

        is DataResult.Success -> updateState {
            it.copy(areas = data.datas, state = State.Idle)
        }
    }

    private fun DataResult<List<Meal>>.mapSavedMeals(): HomeViewState = when (this) {
        is DataResult.Failure -> updateState {
            it.copy(message = error.localizedMessage ?: error.toString(), state = State.Failed)
        }

        is DataResult.Success -> updateState {
            it.copy(savedMeals = data, state = State.Idle)
        }
    }
}