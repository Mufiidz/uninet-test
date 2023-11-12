package id.my.mufidz.authtest.screen.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.authtest.base.BaseViewModel
import id.my.mufidz.authtest.usecase.UsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val usersUseCase: UsersUseCase) :
    BaseViewModel<HomeViewState, HomeAction, HomeResult>(
        HomeViewState()
    ) {
    override fun HomeResult.updateViewState(): HomeViewState = when (this) {
        is HomeResult.ListUsers -> updateState {
            it.copy(users = MutableStateFlow(result))
        }
    }

    override fun HomeAction.handleAction(): Flow<HomeResult> = channelFlow {
        when (this@handleAction) {
            HomeAction.LoadHomeData -> {
                usersUseCase.getResult().also {
                    it.cachedIn(viewModelScope).collectLatest { data ->
                        send(HomeResult.ListUsers(data))
                    }
                }
            }
        }
    }
}