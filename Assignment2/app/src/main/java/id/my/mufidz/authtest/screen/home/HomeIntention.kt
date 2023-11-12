package id.my.mufidz.authtest.screen.home

import androidx.paging.PagingData
import id.my.mufidz.authtest.base.ActionResult
import id.my.mufidz.authtest.base.ViewAction
import id.my.mufidz.authtest.base.ViewState
import id.my.mufidz.authtest.model.User
import kotlinx.coroutines.flow.MutableStateFlow

sealed class HomeAction : ViewAction {
    data object LoadHomeData : HomeAction()
}

sealed class HomeResult : ActionResult {
    data class ListUsers(val result: PagingData<User>) : HomeResult()
}

data class HomeViewState(
    val users: MutableStateFlow<PagingData<User>> = MutableStateFlow(PagingData.empty()),
) : ViewState
