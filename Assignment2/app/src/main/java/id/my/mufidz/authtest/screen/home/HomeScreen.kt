package id.my.mufidz.authtest.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import id.my.mufidz.authtest.base.component.LoadingContent
import id.my.mufidz.authtest.base.component.itemsPaging

@Destination
@Composable
fun HomeScreen() {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    homeViewModel.execute(HomeAction.LoadHomeData)

    HomeScreenContent(homeViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(homeViewModel: HomeViewModel) {
    val state = homeViewModel.viewState.collectAsState().value
    val usersPagingData = state.users.collectAsLazyPagingItems()
    val pagingState = usersPagingData.loadState
    val statePrepend = pagingState.prepend

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "Home") }) }
    ) { paddingValues ->
        when (statePrepend) {
            is LoadState.Error -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .background(Color.Red),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = (statePrepend.error.localizedMessage ?: "Error"), color = Color.White)
            }

            LoadState.Loading -> LoadingContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            )

            is LoadState.NotLoading -> {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                ) {
                    itemsPaging(usersPagingData) {
                        ItemUser(user = it)
                    }
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            when (val stateAppend = pagingState.append) {
                                is LoadState.Error -> {
                                    val error = stateAppend.error
                                    Text(text = error.localizedMessage ?: error.toString())
                                    TextButton(onClick = { usersPagingData.retry() }) {
                                        Text(text = "Retry")
                                    }
                                }

                                LoadState.Loading -> {
                                    CircularProgressIndicator(Modifier.padding(8.dp))
                                }

                                is LoadState.NotLoading -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}
