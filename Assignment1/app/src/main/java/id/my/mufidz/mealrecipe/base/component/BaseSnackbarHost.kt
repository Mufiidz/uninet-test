package id.my.mufidz.mealrecipe.base.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import id.my.mufidz.mealrecipe.base.State

@Composable
fun BaseSnackbarHost(hostState: SnackbarHostState, state: State) {
    SnackbarHost(hostState) {
        val (containerColor, contentColor) = when (state) {
            State.Success -> Pair(MaterialTheme.colorScheme.tertiaryContainer, MaterialTheme.colorScheme.onTertiaryContainer)
            State.Failed -> Pair(
                MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.onError
            )

            else -> Pair(SnackbarDefaults.color, SnackbarDefaults.contentColor)
        }
        Snackbar(
            containerColor = containerColor, contentColor = contentColor, snackbarData = it
        )
    }
}