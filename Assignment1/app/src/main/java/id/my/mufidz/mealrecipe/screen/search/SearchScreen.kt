package id.my.mufidz.mealrecipe.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import id.my.mufidz.mealrecipe.base.State
import id.my.mufidz.mealrecipe.base.component.CenterContent
import id.my.mufidz.mealrecipe.base.component.LoadingContent
import id.my.mufidz.mealrecipe.screen.destinations.DetailScreenDestination
import id.my.mufidz.mealrecipe.screen.meals.ItemMeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun SearchScreen(navigator: DestinationsNavigator) {
    val searchViewModel = hiltViewModel<SearchViewModel>()
    val state = searchViewModel.viewState.collectAsState().value
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(true) }

    Scaffold(topBar = {
        DockedSearchBar(query = query,
            onQueryChange = {
                query = it
                if (!active) {
                    active = true
                }
            },
            onSearch = {
                query = it
            },
            active = false,
            onActiveChange = { active = it },
            shape = RectangleShape,
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                IconButton(onClick = { navigator.navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            trailingIcon = {
                if (active) {
                    Row {
                        IconButton(onClick = {
                            if (query.isNotEmpty()) query = "" else active = false
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                        }
                        IconButton(onClick = {
                            if (query.isNotEmpty()) {
                                searchViewModel.execute(SearchAction.SearchMeal(query))
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                        }
                    }
                }
            }) {}
    }) { paddingValues ->
        when {
            state.state == State.Loading -> {
                LoadingContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            state.meals.isNotEmpty() -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(state.meals) {
                        ItemMeal(meal = it) {
                            navigator.navigate(DetailScreenDestination(it))
                        }
                    }
                }
            }

            else -> {
                CenterContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Text(text = "Tidak ada Data")
                }
            }
        }
    }
}