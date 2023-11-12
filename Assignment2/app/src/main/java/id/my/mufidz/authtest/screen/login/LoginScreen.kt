package id.my.mufidz.authtest.screen.login

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.ActivityNavigatorExtras
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import id.my.mufidz.authtest.base.component.EditTextField
import id.my.mufidz.authtest.base.component.LoadingContent
import id.my.mufidz.authtest.model.UserDto
import id.my.mufidz.authtest.screen.destinations.HomeScreenDestination
import id.my.mufidz.authtest.screen.destinations.LoginScreenDestination
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(true)
@Destination
@Composable
fun LoginScreen(navigator: DestinationsNavigator) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val state = loginViewModel.viewState.collectAsState().value
    val snackbarHost = remember { SnackbarHostState() }
    var showPassword by remember { mutableStateOf(false) }

    when (state.state) {
        State.Success -> {
            LaunchedEffect(snackbarHost) {
                snackbarHost.showSnackbar(state.message)
            }
            navigator.navigate(HomeScreenDestination) {
                popUpTo(LoginScreenDestination) {
                    inclusive = true
                }
            }
        }

        State.Failed -> {
            LaunchedEffect(snackbarHost) {
                snackbarHost.showSnackbar(state.message)
            }
        }

        else -> {}
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmptyEmail by remember { mutableStateOf(false) }
    var isEmptyPassword by remember { mutableStateOf(false) }

    val isEnabled = email.isNotEmpty() && password.isNotEmpty()

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "Login") }) },
        snackbarHost = {
            SnackbarHost(snackbarHost) {
                val (containerColor, contentColor) = when (state.state) {
                    State.Success -> Pair(Color.Green, Color.White)
                    State.Failed -> Pair(
                        MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.errorContainer
                    )

                    else -> Pair(SnackbarDefaults.color, SnackbarDefaults.contentColor)
                }
                Snackbar(
                    containerColor = containerColor, contentColor = contentColor, snackbarData = it
                )
            }
        },
    ) { paddingValues ->
        if (state.state == State.Loading) {
            LoadingContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            )
        } else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditTextField(
                    email,
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    label = "Email",
                    hint = "email@mail.com",
                    isError = isEmptyEmail
                ) {
                    isEmptyEmail = it.isEmpty()
                    email = it.trim()
                }
                EditTextField(
                    password,
                    Modifier.fillMaxWidth(),
                    label = "Password",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Password Visibility"
                            )
                        }
                    },
                    isError = isEmptyPassword
                ) {
                    isEmptyPassword = it.isEmpty()
                    password = it.trim()
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button({
                    val user = UserDto(email, password).also { Timber.d(it.toString()) }
                    loginViewModel.execute(LoginAction.LoginUser(user))
                }, Modifier.fillMaxWidth(), enabled = isEnabled) {
                    Text(text = "Login")
                }
            }
        }
    }
}
