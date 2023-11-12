package id.my.mufidz.mealrecipe.screen.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.mealrecipe.base.BaseViewModel
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.local.MealLocalRepository
import id.my.mufidz.mealrecipe.data.network.MealRepository
import id.my.mufidz.mealrecipe.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    private val mealLocalRepository: MealLocalRepository
) : BaseViewModel<DetailViewState, DetailAction, DetailResult>(
    DetailViewState()
) {

    override fun DetailResult.updateViewState(): DetailViewState = when (this) {
        is DetailResult.DetailMeal -> result.mapResult()
        is DetailResult.SaveMeal -> result.saveMealResult(isSaved)
    }

    override fun DetailAction.handleAction(): Flow<DetailResult> = channelFlow {
        when (this@handleAction) {
            is DetailAction.GetDetail -> {
                updateState { it.copy(state = State.Loading) }
                val response = mealLocalRepository.getMealById(id)
                val mealLocal = (response as DataResult.Success).data

                updateState { it.copy(isSaved = mealLocal != null) }

                if (mealLocal == null) {
                    mealRepository.detailById(id).also {
                        send(DetailResult.DetailMeal(it))
                    }
                } else {
                    send(DetailResult.DetailMeal(response))
                }
            }
            is DetailAction.DeleteMeal -> {
                updateState { it.copy(state = State.Loading) }
                mealLocalRepository.deleteMeal(meal).also {
                    send(DetailResult.SaveMeal(it, false))
                }
            }
            is DetailAction.SaveMeal -> {
                updateState { it.copy(state = State.Loading) }
                mealLocalRepository.upsertMeal(meal).also {
                    send(DetailResult.SaveMeal(it, true))
                }
            }
        }
    }

    private fun DataResult<Meal?>.mapResult(): DetailViewState = when (this) {
        is DataResult.Failure -> updateState {
            it.copy(
                state = State.Failed, message = error.localizedMessage ?: error.toString()
            )
        }

        is DataResult.Success -> updateState {
            it.copy(state = State.Idle, meal = data)
        }
    }

    private fun DataResult<String>.saveMealResult(isSaved: Boolean): DetailViewState = when (this) {
        is DataResult.Failure -> updateState {
            it.copy(
                state = State.Failed, message = error.localizedMessage ?: error.toString()
            )
        }

        is DataResult.Success -> updateState {
            it.copy(state = State.Success, message = data, isSaved = isSaved)
        }
    }
}