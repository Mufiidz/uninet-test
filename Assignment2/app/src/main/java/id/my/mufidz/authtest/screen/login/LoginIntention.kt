package id.my.mufidz.authtest.screen.login

import id.my.mufidz.authtest.base.ActionResult
import id.my.mufidz.authtest.base.ViewAction
import id.my.mufidz.authtest.base.ViewState
import id.my.mufidz.authtest.model.UserDto
import id.my.mufidz.authtest.usecase.LoginUseCase

sealed class LoginAction : ViewAction {
    data class LoginUser(val userDto: UserDto) : LoginAction()
}

sealed class LoginResult : ActionResult {
    data class LoginData(val result: LoginUseCase.Result) : LoginResult()
}

data class LoginViewState(val message: String = "", val state: State = State.Idle) : ViewState

enum class State {
    Idle, Loading, Success, Failed
}