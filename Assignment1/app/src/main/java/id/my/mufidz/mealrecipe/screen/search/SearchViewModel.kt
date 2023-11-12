package id.my.mufidz.mealrecipe.screen.search

import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.mealrecipe.base.BaseViewModel
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.data.DataResult
import id.my.mufidz.mealrecipe.data.network.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val mealRepository: MealRepository) :
    BaseViewModel<SearchViewState, SearchAction, SearchResult>(
        SearchViewState()
    ) {
    override fun SearchResult.updateViewState(): SearchViewState = when (this) {
        is SearchResult.MealsResult -> when (result) {
            is DataResult.Failure -> updateState {
                it.copy(
                    state = State.Failed,
                    message = result.error.localizedMessage ?: result.error.toString()
                )
            }

            is DataResult.Success -> updateState {
                it.copy(state = State.Idle, meals = result.data.datas)
            }
        }
    }

    override fun SearchAction.handleAction(): Flow<SearchResult> = channelFlow {
        when (this@handleAction) {
            is SearchAction.SearchMeal -> {
                updateState { it.copy(state = State.Loading) }
                mealRepository.searchByName(query).also {
                    send(SearchResult.MealsResult(it))
                }
            }
        }
    }
}