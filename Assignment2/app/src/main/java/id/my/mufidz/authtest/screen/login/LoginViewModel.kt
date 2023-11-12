package id.my.mufidz.authtest.screen.login

import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.mufidz.authtest.base.BaseViewModel
import id.my.mufidz.authtest.usecase.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: LoginUseCase) :
    BaseViewModel<LoginViewState, LoginAction, LoginResult>(LoginViewState()) {

    override fun LoginResult.updateViewState(): LoginViewState = when (this) {
        is LoginResult.LoginData -> updateState {
            when (result) {
                is LoginUseCase.Result.Error -> it.copy(
                    message = result.errorMessage, state = State.Failed
                )

                is LoginUseCase.Result.Success -> it.copy(
                    message = "Berhasil Login", state = State.Success
                )
            }
        }
    }

    override fun LoginAction.handleAction(): Flow<LoginResult> = channelFlow {
        when (this@handleAction) {
            is LoginAction.LoginUser -> {
                updateState { it.copy(state = State.Loading) }
                delay(500)
                useCase.getResult(userDto).also {
                    send(LoginResult.LoginData(it))
                }
            }
        }
    }
}