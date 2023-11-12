package id.my.mufidz.authtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import id.my.mufidz.authtest.screen.NavGraphs
import id.my.mufidz.authtest.screen.destinations.HomeScreenDestination
import id.my.mufidz.authtest.screen.destinations.LoginScreenDestination
import id.my.mufidz.authtest.ui.theme.AuthTestTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var dataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokenKey = stringPreferencesKey("token")
        val preferences = runBlocking { dataStore.data.first() }
        val token = preferences[tokenKey]
        val isLoggedIn = !token.isNullOrEmpty()
        setContent {
            AuthTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        startRoute = if (!isLoggedIn) LoginScreenDestination else HomeScreenDestination
                    )
                }
            }
        }
    }
}